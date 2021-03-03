package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.event.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SimpleDomainEventPublisher implements DomainEventPublisher, ApplicationContextAware {

    public SimpleDomainEventPublisher(DomainEventRepository domainEventRepository) {
        this.domainEventRepository = domainEventRepository;
        //新启动一个线程补偿
        log.info("Trigger events remedy action at Thread {}", Thread.currentThread().getName());
        //TODO 如何实现补偿，因为事务问题，暂时没有很好的办法
        ThreadUtil.execAsync(new Runnable() {
            @Override
            public void run() {
                //事务在多线程下不工作 详细： https://blog.51cto.com/13175304/1952201 和代码：TransactionSynchronizationManager

                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                PlatformTransactionManager txManager = ContextLoader.getCurrentWebApplicationContext().getBean(PlatformTransactionManager.class);
                TransactionStatus status = txManager.getTransaction(def);

                try {
                    SimpleDomainEventPublisher.this.eventualConsistencyRemedyAction();
                    txManager.commit(status); // 提交事务
                } catch (Exception e) {
                    log.info("异常信息：" + e.toString());
                    txManager.rollback(status); // 回滚事务
                }
            }
        }, true);
    }

    private ApplicationContext applicationContext;

    private Set<EventHandler> eventHandlers = new HashSet<EventHandler>();

    public void registerEventHandler(EventHandler handler) {
        eventHandlers.add(handler);
    }

    private final DomainEventRepository domainEventRepository;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = this.applicationContext;
    }

    /**
     * 事件处理必须保持与外部事务一起工作
     *
     * @param event
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void publish(Serializable event) {
        SimpleDomainEvent simpleDomainEvent = new SimpleDomainEvent(AggregateId.generate(), DateUtil.date(), false, event);
        domainEventRepository.save(simpleDomainEvent);
        try {
            //任何实际分发的异常，都不回导致当前事务回滚，确保 "事件保存是成功的"
            internalDoPublish(simpleDomainEvent);
        } catch (Throwable e) {
            //
        }

    }


    @Transactional
    public void internalDoPublish(SimpleDomainEvent eventEntity) {
        final Serializable sourceEvent = eventEntity.getSourceEvent();
        for (EventHandler handler : new ArrayList<EventHandler>(eventHandlers)) {
            if (handler.canHandle(sourceEvent)) {
                try {
                    handler.handle(sourceEvent);
                } catch (Exception e) {
                    log.error("event handling error", e);
                }
            }
        }
        eventEntity.markAsPublished();
        domainEventRepository.save(eventEntity);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle() {
        log.info("Start events remedy action at Thread {}", Thread.currentThread().getName());
        Collection<SimpleDomainEvent> simpleDomainEvents = domainEventRepository.fetchNonPublishEvents();
        try {
            simpleDomainEvents.stream().forEach(this::internalDoPublish);
            log.info("### READ EVENTS AND PUBLISHED ### {}", Thread.currentThread().getName());
            //TODO 如何忽略无法处理和读取的事件
        } catch (Exception e) {
            log.error("补偿事件", e);
        }
    }

    /**
     * 循环读取未发布事件并再次发出
     */
    @Transactional
    public void eventualConsistencyRemedyAction() {
        log.info("Start events remedy action at Thread {}", Thread.currentThread().getName());
        while (true) {
            Collection<SimpleDomainEvent> simpleDomainEvents = domainEventRepository.fetchNonPublishEvents();
            try {
                simpleDomainEvents.stream().forEach(this::internalDoPublish);
                log.info("### READ EVENTS AND PUBLISHED ### {}", Thread.currentThread().getName());
                //TODO 如何忽略无法处理和读取的事件
            } catch (Exception e) {
                log.error("补偿事件", e);
            }

            //没有事件，暂停三秒
            if (simpleDomainEvents == null || simpleDomainEvents.isEmpty()) {
                ThreadUtil.safeSleep(3 * 1000);
            }
        }
    }
}
