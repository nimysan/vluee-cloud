package com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa;

import com.vluee.cloud.commons.ddd.support.domain.SimpleDomainEvent;
import com.vluee.cloud.commons.ddd.support.domain.DomainEventRepository;

public class JpaDomainEventRepository extends GenericJpaRepository<SimpleDomainEvent> implements DomainEventRepository {
}
