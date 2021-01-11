package com.vluee.cloud.commons.common.data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TenancyAndAuditAware extends AuditAware {

    private String tenancyId;
    public TenancyAndAuditAware(){}

    public TenancyAndAuditAware(String tenancyId) {
        super();
        this.tenancyId = tenancyId;
    }

    public String getTenancyId() {
        return this.tenancyId;
    }

}
