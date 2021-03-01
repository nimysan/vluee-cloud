package com.vluee.cloud.uams.core.rbac.domain;

import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import com.vluee.cloud.uams.core.role.domain.CRole;

import java.util.Collection;
import java.util.Collections;

@ValueObject
public class RoleOwner {

    public RoleOwner(OwnerType type, Collection<CRole> roles) {
        this.type = type;
        if (roles == null) {
            this.roles = Collections.emptyList();
        } else {
            this.roles = Collections.unmodifiableCollection(roles);
        }
    }

    private final OwnerType type;

    public final Collection<CRole> roles;

    public Collection<CRole> getRoles() {
        return roles;
    }

    enum OwnerType {
        SYSTEM_USER, APP_CLIENT
    }

}
