package com.vluee.cloud.tenants.core.tenants.exception;

/**
 * 租户名称已经存在的异常
 */
public class TenantNameExistException extends RuntimeException {

    private String tenantName;

    public TenantNameExistException(String tenantName) {
        super("租户 [" + tenantName + "]已存在");
    }


}
