package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import com.vluee.cloud.commons.ddd.support.event.DelegateDomainEventSender;
import com.vluee.cloud.commons.ddd.support.event.DomainEventFactory;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class DefaultDomainEventSender implements DelegateDomainEventSender {

    private final DomainEventRepository domainEventRepository;
    private final DomainEventFactory domainEventFactory;

    public DefaultDomainEventSender(DomainEventRepository domainEventRepository, DomainEventFactory domainEventFactory) {
        this.domainEventRepository = domainEventRepository;
        this.domainEventFactory = domainEventFactory;
    }

    private Set<EventHandler> eventHandlers = new HashSet<EventHandler>();

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendEvent(SimpleDomainEvent eventEntity) {
        {
            boolean publishDone = true;
            final Serializable sourceEvent = eventEntity.getSourceEvent(domainEventFactory);
            publishDone = realSendEventOut(sourceEvent);

            if (publishDone) {
                SimpleDomainEvent managedEvent = domainEventRepository.load(eventEntity.getAggregateId());
                managedEvent.markAsPublished(); //标记后， JPA会自动save修改进数据库
            } else {
                SimpleDomainEvent managedEvent = domainEventRepository.load(eventEntity.getAggregateId());
                managedEvent.incrementRetries();
            }
        }
    }

    protected boolean realSendEventOut(Serializable sourceEvent) {
        for (EventHandler handler : new ArrayList<EventHandler>(eventHandlers)) {
            if (handler.canHandle(sourceEvent)) {
                try {
                    log.info("Handler event {} by handler {}", sourceEvent, handler);
                    handler.handle(sourceEvent);
                } catch (Throwable e) {
                    log.error("event handling error", e);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void add(EventHandler handler) {
        this.eventHandlers.add(handler);
    }

}
