package com.vluee.cloud.tenants.core.tenant.domain;

import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@GenericGenerator(name = LongIdGenerator.ID_GENERATOR_NAME, strategy = LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
@NoArgsConstructor
public class Tenant extends AuditAware {

    @Id
    @GeneratedValue(generator = LongIdGenerator.ID_GENERATOR_NAME)
    private Long id;

    public Tenant(@NotBlank String tenantName) {
        this.tenantName = tenantName;
    }

    private String tenantName;

    public String getTenantName() {
        return tenantName;
    }

    public Long getId() {
        return id;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
