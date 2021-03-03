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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        ThreadUtil.execAsync(new Runnable() {
            @Override
            public void run() {
                //事务在多线程下不工作 详细： https://blog.51cto.com/13175304/1952201 和代码：TransactionSynchronizationManager
                //SimpleDomainEventPublisher.this.eventualConsistencyRemedyAction();
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void publish(Serializable event) {
        SimpleDomainEvent simpleDomainEvent = new SimpleDomainEvent(AggregateId.generate(), DateUtil.date(), false, event);
        domainEventRepository.save(simpleDomainEvent);
        log.info("Event is saved ... {}", simpleDomainEvent);
        internalDoPublish(simpleDomainEvent);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
