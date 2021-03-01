package com.vluee.cloud.commons.ddd.support.domain;

public interface DomainEventRepository {
    public void save(SimpleDomainEvent domainEvent);
}
