package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;

public interface DomainEventRepository {
    public void save(SimpleDomainEvent domainEvent);
}
