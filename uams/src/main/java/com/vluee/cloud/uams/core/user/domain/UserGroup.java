package com.vluee.cloud.uams.core.user.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import com.vluee.cloud.uams.core.role.domain.CRole;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * 用户组
 */
@Entity
@AggregateRoot
@NoArgsConstructor // for jpa
public class UserGroup extends BaseAggregateRoot {

    public UserGroup(AggregateId aggregateId, String name) {
        this.groupName = name;
        this.aggregateId = aggregateId;
        this.status = UserGroupStatus.ENABLE;
    }

    @Column(length = 128)
    public String groupName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userGroupRoleGrantID.userGroupId")
    private Set<UserGroupRoleGrant> userGroupRoleGrants;

    @Enumerated
    public UserGroupStatus status;

    public void grantRole(CRole role) {
        UserGroupRoleGrant userGroupRoleGrant = new UserGroupRoleGrant(this, role);
        userGroupRoleGrants.add(userGroupRoleGrant);
    }

    /**
     * 用户组是否可用
     *
     * @return
     */
    public boolean isActive() {
        return UserGroupStatus.ENABLE.equals(this.status);
    }

    enum UserGroupStatus {
        ENABLE, DISABLED
    }
}
