package com.vluee.cloud.tenants.core.tenant.domain;

import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import com.vluee.cloud.tenants.core.brand.domain.Brand;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Tenant extends AuditAware {

    @Id
    @GeneratedValue(generator = LongIdGenerator.ID_GENERATOR_NAME)
    private Serializable id;

    public Tenant(@NotBlank String tenantName) {
        this.tenantName = tenantName;
    }

    private String tenantName;

    @OneToMany
    @JoinTable(name = "tenant_brand_maps", joinColumns = {@JoinColumn(name = "brand_id")})
    private Set<Brand> brands = new HashSet<>(2);

    public Tenant(Set<Brand> brands) {
        this.brands = brands;
    }

    public String getTenantName() {
        return tenantName;
    }

    public Serializable getId() {
        return id;
    }

    public Collection<Brand> getBrands() {
        return Collections.unmodifiableSet(this.brands);
    }

    public void addBrand(Brand brand) {
        brands.add(brand);
    }
}
