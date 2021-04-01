package com.vluee.cloud.commons.ddd.support.infrastructure.events.kafka;

import com.vluee.cloud.commons.ddd.support.event.DomainEvent;
import com.vluee.cloud.commons.ddd.support.event.DomainEventFactory;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;
import com.vluee.cloud.commons.ddd.support.infrastructure.events.DefaultDomainEventSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Serializable;
import java.util.concurrent.Future;

@Slf4j
public class KafkaDomainEventSender extends DefaultDomainEventSender {

    private final Producer<Integer, Object> kafkaProducer;

    private final DomainEventSerializer domainEventSerializer;

    public KafkaDomainEventSender(DomainEventRepository domainEventRepository, DomainEventFactory domainEventFactory, Producer<Integer, Object> kafkaProducer, DomainEventSerializer domainEventSerializer) {
        super(domainEventRepository, domainEventFactory);
        this.kafkaProducer = kafkaProducer;
        this.domainEventSerializer = domainEventSerializer;
    }

    @Override
    protected boolean realSendEventOut(Serializable sourceEvent) {
        try {
            Future future = kafkaProducer.send(new ProducerRecord(DomainEvent.DOMAIN_EVENT_TOPIC, domainEventSerializer.serialize(sourceEvent)));
        } catch (Exception exception) {
            log.error("Failed to send the domain event by kafka", exception);
            return false;
        }
        return true;
    }
}
