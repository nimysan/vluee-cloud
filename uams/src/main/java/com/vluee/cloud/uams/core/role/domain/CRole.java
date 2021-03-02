package com.vluee.cloud.uams.core.role.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import com.vluee.cloud.uams.core.permission.domain.ApiPermission;
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
import java.util.List;
import java.util.stream.Collectors;

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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Collection<RolePermissionGrant> apiPermissionGrants = new HashSet<>();

    public boolean hasPermission(@NotNull AggregateId permissionId) {
        return apiPermissionGrants != null && this.apiPermissionGrants.contains(permissionId);
    }

    public void grantApiPermission(@NotNull ApiPermission apiPermission) {
        RolePermissionGrant rolePermissionGrant = new RolePermissionGrant(this, apiPermission);
        apiPermissionGrants.add(rolePermissionGrant);
        publish(new RolePermissionAddedEvent(this.getAggregateId(), apiPermission.getAggregateId()));
    }

    public void cancelApiPermissionGrant(@NotNull AggregateId permissionId) {
        List<RolePermissionGrant> collect = apiPermissionGrants.stream().filter(t -> t.getApiPermission().equals(permissionId)).collect(Collectors.toList());
        apiPermissionGrants.removeAll(collect);
        publish(new RolePermissionRemovedEvent(this.getAggregateId(), permissionId));
    }

}
