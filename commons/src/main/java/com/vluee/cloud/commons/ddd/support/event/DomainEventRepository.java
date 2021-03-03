package com.vluee.cloud.commons.ddd.support.event;

import java.util.Collection;

public interface DomainEventRepository {

    public void save(SimpleDomainEvent domainEvent);

    Collection<SimpleDomainEvent> fetchNonPublishEvents();
}
