package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import cn.hutool.core.thread.ThreadUtil;
import com.vluee.cloud.commons.ddd.support.event.DelegateDomainEventSender;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
public class DefaultDomainEventSender implements DelegateDomainEventSender, ApplicationListener<ContextRefreshedEvent> {

    private final DomainEventRepository domainEventRepository;

    public DefaultDomainEventSender(DomainEventRepository domainEventRepository) {
        this.domainEventRepository = domainEventRepository;
    }

    private Set<EventHandler> eventHandlers = new HashSet<EventHandler>();

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendEvent(SimpleDomainEvent eventEntity) {
        {
            boolean publishDone = true;
            final Serializable sourceEvent = eventEntity.getSourceEvent();
            for (EventHandler handler : new ArrayList<EventHandler>(eventHandlers)) {
                if (handler.canHandle(sourceEvent)) {
                    try {
                        log.info("Handler event {} by handler {}", sourceEvent, handler);
                        handler.handle(sourceEvent);
                    } catch (Throwable e) {
                        log.error("event handling error", e);
                        publishDone = false;
                    }
                }
            }

            if (publishDone) {
                SimpleDomainEvent managedEvent = domainEventRepository.load(eventEntity.getAggregateId());
                managedEvent.markAsPublished(); //标记后， JPA会自动save修改进数据库
            }
        }
    }

    @Override
    public void add(EventHandler handler) {
        this.eventHandlers.add(handler);
    }

    public void resendForever() {
        //新启动一个线程补偿
        log.info("Trigger events remedy action at Thread {}", Thread.currentThread().getName());
        //TODO 如何实现补偿，因为事务问题，暂时没有很好的办法
        ThreadUtil.execAsync(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //事务在多线程下不工作 详细： https://blog.51cto.com/13175304/1952201 和代码：TransactionSynchronizationManager
                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    PlatformTransactionManager txManager = ContextLoader.getCurrentWebApplicationContext().getBean(PlatformTransactionManager.class);
                    TransactionStatus status = txManager.getTransaction(def);
                    Collection<SimpleDomainEvent> simpleDomainEvents = null;
                    try {
                        simpleDomainEvents = domainEventRepository.fetchNonPublishEvents();
                        for (SimpleDomainEvent simpleDomainEvent : simpleDomainEvents) {
                            DefaultDomainEventSender.this.sendEvent(simpleDomainEvent);
                        }
                        txManager.commit(status); // 提交事务
                    } catch (Exception e) {
                        log.info("异常信息：" + e.toString());
                        txManager.rollback(status); // 回滚事务
                    } finally {
                        if (simpleDomainEvents == null || simpleDomainEvents.isEmpty()) {
                            ThreadUtil.safeSleep(10 * 1000); // 如果没有事件了，则暂停10s
                        }
                    }
                }

            }
        }, true);
    }

    /**
     * 程序加载完成后执行
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Domain event daemon started");
        resendForever();
    }
}
