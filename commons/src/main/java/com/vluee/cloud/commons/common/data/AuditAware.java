package com.vluee.cloud.commons.common.data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 *
 */
@MappedSuperclass
public class AuditAware {

    /**
     * 记录创建时间
     */
    private Date createdAt;

    /**
     * 记录创建人
     */
    private String createdBy;

    /**
     * 最后修改时间
     */
    private Date lastModifiedAt;

    /**
     * 最后修改人
     */
    private String lastModifiedBy;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
