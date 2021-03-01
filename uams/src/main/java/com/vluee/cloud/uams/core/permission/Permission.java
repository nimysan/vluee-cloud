package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import com.vluee.cloud.uams.core.role.domain.CRole;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * Operation + Resource构成Permission
 */
public class Permission extends BaseAggregateRoot {

    @Getter
    private Operation operation;

    @Getter
    private Resource resource;

    @Getter
    private boolean disabled = false;

    public Permission(@NotNull AggregateId aggregateId, @NotNull Operation operation, @NotNull Resource resource) {
        this.operation = operation;
        this.resource = resource;
        this.aggregateId = aggregateId;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "operation=" + operation +
                ", resource=" + resource +
                '}';
    }

    public void enable() {
        this.disabled = false;
    }

    public void disable() {
        this.disabled = true;
    }

    public boolean isGrantToRole(CRole role) {
        return true;
    }
}
