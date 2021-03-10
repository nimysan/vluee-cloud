package com.vluee.cloud.uams.core.permission.domain;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import com.vluee.cloud.uams.core.resources.domain.ApiResource;
import com.vluee.cloud.uams.core.resources.domain.ResourceOperation;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Operation + Resource构成Permission
 */
@Entity
@Table(name = "api_permissions")
public class ApiPermission extends BaseAggregateRoot {

    //for jpa only
    public ApiPermission() {
        this.resourceOperation = null;
        this.resourceId = null;
        this.createdAt = null;
    }

    @Getter
    @Embedded
    private final ResourceOperation resourceOperation;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "resourceId", nullable = false))})
    private final AggregateId resourceId;

    @Getter
    @Column
    private boolean disabled = false;

    @Getter
    @Column
    private final Date createdAt;

    public ApiPermission(@NotNull AggregateId aggregateId, @NotNull ResourceOperation resourceOperation, @NotNull ApiResource resource) {
        this.resourceOperation = resourceOperation;
        this.resourceId = resource.getAggregateId();
        this.aggregateId = aggregateId;
        this.createdAt = DateUtil.date();
    }

    @Override
    public String toString() {
        return "ApiPermission{" +
                "operation=" + resourceOperation +
                ", resourceId=" + resourceId +
                ", disabled=" + disabled +
                '}';
    }
}
