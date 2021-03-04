package com.vluee.cloud.commons.ddd.support.event;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

import java.util.Collection;

public interface DomainEventRepository {

    public void save(SimpleDomainEvent domainEvent);

    Collection<SimpleDomainEvent> fetchNonPublishEvents();

    SimpleDomainEvent load(AggregateId aggregateId);
}
