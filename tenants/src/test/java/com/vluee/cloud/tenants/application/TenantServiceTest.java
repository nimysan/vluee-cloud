package com.vluee.cloud.tenants.application;

import com.vluee.cloud.tenants.core.brand.domain.Brand;
import com.vluee.cloud.tenants.core.tenant.domain.Tenant;
import com.vluee.cloud.tenants.core.tenant.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@SpringBootTest
@Slf4j
class TenantServiceTest {

    @Autowired
    private TenantService tenantService;

    @Test
    @Transactional
    public void addBrand() {
        Tenant tenant = tenantService.addTenant("微笑连锁");
        Brand brand = tenantService.addBrand(tenant.getId(), "锦江之星");
        Assertions.assertNotNull(brand);


        Tenant finalTenant = tenantService.getTenant(tenant.getId());
        Assertions.assertEquals(1, finalTenant.getBrands().size());
    }

    @Test
    @Transactional
    public void addChildBrand() {
        Tenant tenant = tenantService.addTenant("微笑连锁");
        Brand brand = tenantService.addBrand(tenant.getId(), "锦江之星");

        tenantService.addChildBrand(brand.getId(), "微笑连锁-大笑");

        Tenant finalTenant = tenantService.getTenant(tenant.getId());
        Collection<Brand> brands = finalTenant.getBrands();
        brands.forEach(t -> Assertions.assertEquals(1, t.getChildren().size()));
    }

}