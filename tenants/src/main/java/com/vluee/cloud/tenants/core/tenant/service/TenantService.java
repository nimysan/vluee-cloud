package com.vluee.cloud.tenants.core.tenant.service;

import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.date.DateUtils;
import com.vluee.cloud.tenants.core.tenant.domain.Tenant;
import com.vluee.cloud.tenants.core.tenant.domain.TenantRepository;
import com.vluee.cloud.tenants.core.tenant.exception.TenantNameExistException;
import com.vluee.cloud.tenants.core.tenant.exception.TenantNotExitException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final AuditContext auditContext;

    @Transactional
    public Tenant addTenant(@NotBlank String tenantName) {

        checkNameDuplicate(tenantName);

        Tenant tenant = new Tenant(tenantName);
        audit(tenant);

        tenantRepository.save(tenant);

        return tenant;
    }

    public void changeTenantName(@NotBlank Long tenantId, @NotBlank String tenantName) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(TenantNotExitException::new);
        checkNameDuplicate(tenantName);
        tenant.setTenantName(tenantName);
        auditModify(tenant);
        tenantRepository.save(tenant);
    }

    private void checkNameDuplicate(String tenantName) {
        boolean isPresent = tenantRepository.findByTenantName(tenantName).isPresent();
        if (isPresent) throw new TenantNameExistException(tenantName);
    }

    public Tenant getByName(String tenantName) {
        return tenantRepository.findByTenantName(tenantName).orElseThrow((Supplier<RuntimeException>) () -> new TenantNotExitException());
    }

    @Transactional
    public Tenant getTenant(@NotBlank Serializable tenantId) {
        return tenantRepository.findById(tenantId).orElseThrow(TenantNotExitException::new);
    }

    private void audit(AuditAware auditAware) {
        auditAware.setCreatedBy(auditContext.getOpId());
        auditAware.setCreatedAt(DateUtils.currentDate());
    }

    private void auditModify(AuditAware auditAware) {
        auditAware.setLastModifiedBy(auditContext.getOpId());
        auditAware.setLastModifiedAt(DateUtils.currentDate());
    }

}
