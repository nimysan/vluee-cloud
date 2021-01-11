package com.vluee.cloud.tenants.application;


import com.vluee.cloud.tenants.core.brand.domain.Brand;
import com.vluee.cloud.tenants.core.tenant.domain.Tenant;

import javax.validation.constraints.NotBlank;

/**
 * 租户和品牌管理服务
 * <p>
 * 租户对应标准SaaS租户的概念
 */
public interface TenantApplicationService {


    /**
     * 创建品牌
     *
     * @param tenantId  租户ID
     * @param brandName 品牌名称
     * @return
     */
    public Brand addBrand(@NotBlank String tenantId, @NotBlank String brandName);

    /**
     * 添加子品牌
     *
     * @param tenantId      租户ID
     * @param parentBrandId 父品牌ID
     * @param brandName     品牌名称
     * @return
     */
    public Brand addChildBrand(@NotBlank String tenantId, @NotBlank String parentBrandId, @NotBlank String brandName);

    /**sss
     * 创建租户
     *
     * @param tenantName 租户名称
     * @return
     */
    public Tenant registerTenant(@NotBlank String tenantName);
}
