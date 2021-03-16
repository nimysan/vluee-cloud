package com.vluee.cloud.tenants.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.tenants.core.tenant.domain.Tenant;
import com.vluee.cloud.tenants.core.tenant.domain.TenantRepository;

@DomainRepositoryImpl
public class JpaTenantRepository extends GenericJpaRepository<Tenant> implements TenantRepository {
}
