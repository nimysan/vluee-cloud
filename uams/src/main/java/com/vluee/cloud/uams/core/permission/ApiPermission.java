package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Operation + Resource构成Permission
 */
@Entity
@Table(name = "api_permissions")
public class ApiPermission extends BaseAggregateRoot {

    @Getter
    @Embedded
    private Operation operation;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    private RestApiResource resource;

    @Getter
    private boolean disabled = false;

    public ApiPermission(@NotNull AggregateId aggregateId, @NotNull Operation operation, @NotNull RestApiResource resource) {
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

}
