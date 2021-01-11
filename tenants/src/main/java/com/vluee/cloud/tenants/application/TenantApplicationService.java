package com.vluee.cloud.tenants.application;


import com.vluee.cloud.tenants.core.tenant.domain.Tenant;

import javax.validation.constraints.NotBlank;

/**
 * 租户和品牌管理服务
 * <p>
 * 租户对应标准SaaS租户的概念
 */
public interface TenantApplicationService {


    /**
     * 创建租户
     *
     * @param tenantName 租户名称
     * @return
     */
    public Tenant registerTenant(@NotBlank String tenantName);
}
