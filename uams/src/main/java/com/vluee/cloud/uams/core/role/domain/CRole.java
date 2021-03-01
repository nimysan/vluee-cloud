package com.vluee.cloud.uams.core.role.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import com.vluee.cloud.uams.core.role.domain.events.RolePermissionAddedEvent;
import com.vluee.cloud.uams.core.role.domain.events.RolePermissionRemovedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

@AggregateRoot
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class CRole extends BaseAggregateRoot {

    public CRole(AggregateId id, String name) {
        this.name = name;
        this.aggregateId = id;
    }

    @Getter
    @Column(name = "role_name")
    private String name;

    @Getter
    @Column(name = "role_describe")
    private String description;


    /**
     * 标识该角色拥有的所有权限
     */
    @Fetch(FetchMode.JOIN)
    @CollectionTable(name = "role_permissions")
    @ElementCollection
    @AttributeOverrides({
            @AttributeOverride(name = "permission_id",
                    column = @Column(name = "aggregate_id")),
            @AttributeOverride(name = "role_id",
                    column = @Column(name = "crole_aggregate_id"))
    })
    private Collection<AggregateId> ownedPermissions = new HashSet<>();

    public boolean hasPermission(@NotNull AggregateId permissionId) {
        return ownedPermissions != null && this.ownedPermissions.contains(permissionId);
    }

    public void addPermission(@NotNull AggregateId permissionId) {
        this.ownedPermissions.add(permissionId);
        publish(new RolePermissionAddedEvent(this.getAggregateId(), permissionId));
    }

    public void removePermission(@NotNull AggregateId permissionId) {
        this.ownedPermissions.remove(permissionId);
        publish(new RolePermissionRemovedEvent(this.getAggregateId(), permissionId));
    }

}
