package com.vluee.cloud.tenants.interfaces.query.vo;

import com.vluee.cloud.tenants.core.tenant.domain.Tenant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TenantVo {

    private String id;
    private String tenantName;

    public static final TenantVo from(Tenant tenant) {
        return new TenantVo(Long.toString(tenant.getId()), tenant.getTenantName());
    }
}
