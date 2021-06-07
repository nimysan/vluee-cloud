package com.vluee.cloud.commons.ddd.support.infrastructure.events.stream;

import com.vluee.cloud.commons.ddd.support.event.DomainEventFactory;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.DefaultDomainEventSender;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Event publish based on Spring Cloud Stream framework and Kafka(Actually)
 */
@Slf4j
public class SpringCloudStreamEventPublisher extends DefaultDomainEventSender {

    private final DomainEventClient domainEventClient;
    private final DomainEventSerializer domainEventSerializer;

    public SpringCloudStreamEventPublisher(DomainEventRepository domainEventRepository, DomainEventFactory domainEventFactory, DomainEventClient domainEventClient, DomainEventSerializer domainEventSerializer) {
        super(domainEventRepository, domainEventFactory);
        this.domainEventClient = domainEventClient;
        this.domainEventSerializer = domainEventSerializer;
    }

    @Override
    public void sendEvent(SimpleDomainEvent event) {
        //TODO how to allow customized metadata?
        domainEventClient.output().send(MessageBuilder.withPayload(event).build());
    }

    @Override
    public void add(EventHandler handler) {
        //TODO how to register event handlers?
    }
}
