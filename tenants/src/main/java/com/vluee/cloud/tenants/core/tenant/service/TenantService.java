package com.vluee.cloud.tenants.core.tenant.service;

import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.date.DateUtils;
import com.vluee.cloud.tenants.core.brand.domain.Brand;
import com.vluee.cloud.tenants.core.brand.domain.BrandRepository;
import com.vluee.cloud.tenants.core.tenant.domain.Tenant;
import com.vluee.cloud.tenants.core.tenant.domain.TenantRepository;
import com.vluee.cloud.tenants.core.tenant.exception.DomainNotExitException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Service
@AllArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final BrandRepository brandRepository;
    private final AuditContext auditContext;

    @Transactional
    public Tenant addTenant(@NotBlank String tenantName) {
        Tenant tenant = new Tenant(tenantName);
        audit(tenant);
        tenantRepository.save(tenant);
        return tenant;
    }

    @Transactional
    public Brand addBrand(@NotBlank Serializable tenantId, @NotBlank String brandName) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(DomainNotExitException::new);

        Brand brand = new Brand(brandName);
        audit(brand);
        Brand entity = brandRepository.save(brand);

        tenant.addBrand(brand);
        tenantRepository.save(tenant);
        return brand;
    }

    /**
     * 增加子品牌
     *
     * @param brandId
     * @param brandName
     * @return
     */
    @Transactional
    public Brand addChildBrand(@NotBlank Serializable brandId, @NotBlank String brandName) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(DomainNotExitException::new);

        Brand childBrand = new Brand(brandName);
        audit(childBrand);
        brand.addChildBrand(childBrand);

        final Brand save = brandRepository.save(childBrand);
        brandRepository.save(brand);
        return save;
    }

    @Transactional
    public Tenant getTenant(@NotBlank Serializable tenantId) {
        return tenantRepository.findById(tenantId).orElseThrow(DomainNotExitException::new);
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
