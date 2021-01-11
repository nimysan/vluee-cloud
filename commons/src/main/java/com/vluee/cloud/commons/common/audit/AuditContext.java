package com.vluee.cloud.commons.common.audit;

import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.date.DateUtils;

/**
 * 获取与个audit相关的context, 主要是提供操作人员ID数据
 */
public interface AuditContext {

    public String getOpId();

    default public void audit(AuditAware auditAware) {
        auditAware.setCreatedBy(getOpId());
        auditAware.setCreatedAt(DateUtils.currentDate());
    }

    default public void auditModify(AuditAware auditAware) {
        auditAware.setLastModifiedBy(getOpId());
        auditAware.setLastModifiedAt(DateUtils.currentDate());
    }

}
