package com.vluee.cloud.uams.core.permission;

import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * Operation + Resource构成Permission
 */
public class Permission {

    @Getter
    private Operation operation;

    @Getter
    private Resource resource;

    public Permission(@NotNull Operation operation, @NotNull Resource resource) {
        this.operation = operation;
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "operation=" + operation +
                ", resource=" + resource +
                '}';
    }
}
