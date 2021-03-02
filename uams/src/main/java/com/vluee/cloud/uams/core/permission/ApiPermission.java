package com.vluee.cloud.uams.core.permission;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
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

    @Getter
    @Embedded
    private final Operation operation;

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

    public ApiPermission(@NotNull AggregateId aggregateId, @NotNull Operation operation, @NotNull ApiResource resource) {
        this.operation = operation;
        this.resourceId = resource.getAggregateId();
        this.aggregateId = aggregateId;
        this.createdAt = DateUtil.date();
    }

    @Override
    public String toString() {
        return "ApiPermission{" +
                "operation=" + operation +
                ", resourceId=" + resourceId +
                ", disabled=" + disabled +
                '}';
    }

}
