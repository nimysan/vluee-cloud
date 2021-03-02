package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.event.DomainEventPublisher;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SpringDomainEventPublisher implements DomainEventPublisher, ApplicationContextAware {

    public SpringDomainEventPublisher(DomainEventRepository domainEventRepository) {
        this.domainEventRepository = domainEventRepository;
    }

    private ApplicationContext applicationContext;

    private Set<EventHandler> eventHandlers = new HashSet<EventHandler>();

    public void registerEventHandler(EventHandler handler) {
        eventHandlers.add(handler);
        // new SpringEventHandler(eventType, beanName, method));
    }

    private final DomainEventRepository domainEventRepository;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = this.applicationContext;
    }

    @Override
    public void publish(Serializable event) {
        SimpleDomainEvent simpleDomainEvent = new SimpleDomainEvent(AggregateId.generate(), event.getClass().getCanonicalName(), DateUtil.date(), false, JSONUtil.toJsonStr(event));
        domainEventRepository.save(simpleDomainEvent);
        log.info("Event is saved ... {}", simpleDomainEvent);

        internalDoPublish(event);

        simpleDomainEvent.markAsPublished();
        domainEventRepository.save(simpleDomainEvent);
    }

    private void internalDoPublish(Serializable event) {
        for (EventHandler handler : new ArrayList<EventHandler>(eventHandlers)) {
            if (handler.canHandle(event)) {
                try {
                    handler.handle(event);
                } catch (Exception e) {
                    log.error("event handling error", e);
                }
            }
        }
    }


    /**
     * 循环读取未发布事件并再次发出
     */
    @Async
    public void eventualConsistencyRemedyAction() {
        while (true) {
            //
            log.info("### READ EVENTS AND PUBLISHED ###");
            ThreadUtil.safeSleep(3 * 1000);
        }
    }
}
