package com.vluee.cloud.tenants.core.tenant.domain;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Optional;

public interface TenantRepository extends CrudRepository<Tenant, Serializable> {
    public Optional<Tenant> findByTenantName(String tenantName);
}
