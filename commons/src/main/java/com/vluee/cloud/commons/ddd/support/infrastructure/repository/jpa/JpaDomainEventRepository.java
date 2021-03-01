package com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.domain.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.domain.SimpleDomainEvent;

@DomainRepositoryImpl
public class JpaDomainEventRepository extends GenericJpaRepository<SimpleDomainEvent> implements DomainEventRepository {
}
