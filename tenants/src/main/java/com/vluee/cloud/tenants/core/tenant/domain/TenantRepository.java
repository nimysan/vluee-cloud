package com.vluee.cloud.tenants.core.tenant.domain;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface TenantRepository extends CrudRepository<Tenant, Serializable> {

}
