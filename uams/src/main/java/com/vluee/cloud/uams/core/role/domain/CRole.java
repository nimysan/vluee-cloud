package com.vluee.cloud.uams.core.role.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@AggregateRoot
@Entity
@Table(name = "roles")
@NoArgsConstructor
@GenericGenerator(name = LongIdGenerator.ID_GENERATOR_NAME, strategy = LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
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
    private Collection<AggregateId> ownedPermissions;

    public boolean hasPermission(@NotNull AggregateId permissionId) {
        return ownedPermissions != null && this.ownedPermissions.contains(permissionId);
    }

    private Set<User> users;

    /**
     * 将用户分配进角色
     *
     * @param user
     */
    public void assignUser(@NotNull User user) {
        if (users.contains(user)) {
            return;
        }
        users.add(user);
    }

    public void grantPermission(@NotNull Permission permission) {
        this.ownedPermissions.add(permission.getAggregateId());
    }

}
