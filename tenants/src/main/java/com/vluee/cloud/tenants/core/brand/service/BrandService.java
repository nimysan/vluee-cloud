package com.vluee.cloud.tenants.core.brand.service;

import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import com.vluee.cloud.commons.common.date.DateUtils;
import com.vluee.cloud.tenants.core.brand.domain.Brand;
import com.vluee.cloud.tenants.core.brand.domain.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    private final LongIdGenerator idGenerator;

    private final AuditContext auditContext;

    public BrandService(@Autowired BrandRepository brandRepository, @Autowired AuditContext auditContext, @Autowired LongIdGenerator idGenerator) {
        this.brandRepository = brandRepository;
        this.auditContext = auditContext;
        this.idGenerator = idGenerator;
    }

    public void addBrand(String tenantId, String brandName) {
        Brand brand = new Brand(brandName);
        audit(brand);
        brandRepository.save(brand);
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
