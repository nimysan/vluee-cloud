package com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;

import java.util.Collection;

@DomainRepositoryImpl
public class JpaDomainEventRepository extends GenericJpaRepository<SimpleDomainEvent> implements DomainEventRepository {
    @Override
    public Collection<SimpleDomainEvent> fetchNonPublishEvents() {
        return entityManager.createQuery("from SimpleDomainEvent as events where events.published=false").getResultList();
    }
}
