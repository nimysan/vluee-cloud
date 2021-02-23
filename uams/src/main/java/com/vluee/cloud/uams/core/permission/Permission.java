package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.uams.core.role.domain.Role;
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

    @Getter
    private boolean disabled = false;

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

    public void enable() {
        this.disabled = false;
    }

    public void disable() {
        this.disabled = true;
    }

    public boolean isGrantToRole(Role role) {
        return true;
    }
}
