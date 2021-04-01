package com.vluee.cloud.tenants.core.tenants.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.common.string.StringUtils;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainFactory;
import com.vluee.cloud.commons.ddd.support.domain.DomainCreatedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DomainFactory
@AllArgsConstructor
@Slf4j
public class TenantFactory {

    private final TenantRepository tenantRepository;

    public Tenant createTenant(String tenantName) {
        if (StringUtils.isNotEmpty(tenantName)) {
            boolean tenantNameAvailable = tenantRepository.isTenantNameExist(tenantName);
            if (tenantNameAvailable) {
                throw new DomainCreatedException("Tenant name with " + tenantName + " is already exist");
            }
            return new Tenant(AggregateId.generate(), tenantName);
        } else {
            throw new DomainCreatedException("Tenant name does not match the rule");
        }
    }
}
