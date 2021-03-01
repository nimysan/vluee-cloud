package com.vluee.cloud.commons.ddd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DomainEventPublisher {

    public void publish(DomainEvent domainEvent);

    default DomainEventPublisher getInstance() {
        return new DomainEventPublisher() {
            private Logger log = LoggerFactory.getLogger("FakeDomainEventPublisher");

            @Override
            public void publish(DomainEvent domainEvent) {
                log.info("Event [{}] is publish", domainEvent);
            }
        };
    }
}
