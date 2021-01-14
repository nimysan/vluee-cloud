package com.vluee.cloud.commons.ddd;

public interface DomainEventPublisher {

    public void publish(DomainEvent domainEvent);
}
