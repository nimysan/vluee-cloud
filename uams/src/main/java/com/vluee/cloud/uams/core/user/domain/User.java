package com.vluee.cloud.uams.core.user.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.user.domain.events.UserRoleGrantedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统用户， 用户类型分三种
 * <p>
 * <ul>
 *  <li>1. 系统级别的用户，比如saas管理人员</li>
 *  <li>2. 租户级别的用户</li>
 * </ul>
 */
@AggregateRoot
@Entity
@NoArgsConstructor // for jpa
public class User extends BaseAggregateRoot {

    public User(AggregateId userId, String username) {
        this.username = username;
        this.aggregateId = userId;
    }

    /**
     * 用户profile
     */
    @Embedded
    private UserProfile userProfile;

    /**
     * 系统级别的唯一用户名
     */
    @Column(length = 255)
    @Getter
    private String username;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userGroupJoinID.userId")
//    @JoinTable(name = "user_group_joins", joinColumns = {
//            @JoinColumn(name = "user_id")
//    })
    private Set<UserGroupJoin> joinGroups;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userRoleGrantID.userId")
    private Set<UserRoleGrant> userRoleGrants;

    /**
     * TODO how to get roles from group
     *
     * @return
     */
    public Collection<AggregateId> belongRoles() {
        Collection<AggregateId> directRoles = userRoleGrants.stream().map(t -> t.getRoleId()).collect(Collectors.toList());
//        joinGroups.stream().flatMap(new Function<UserGroupJoin, Stream<?>>() {
//            @Override
//            public Stream<?> apply(UserGroupJoin userGroupJoin) {
//                return userGroupJoin.getGroupId();
//            }
//        });
        return directRoles;
    }

    public void grantRole(CRole role) {
        UserRoleGrant userGroupGrant = new UserRoleGrant(this, role);
        userRoleGrants.add(userGroupGrant);
        publish(new UserRoleGrantedEvent(this.getAggregateId(), role.getAggregateId()));
    }

    public void joinGroup(UserGroup group) {
        UserGroupJoin join = new UserGroupJoin(this, group);
        joinGroups.add(join);
    }

    public void leaveGroup(UserGroup group) {
        List<UserGroupJoin> collect = joinGroups.stream().filter(t -> t.getGroupId().equals(group.getAggregateId())).collect(Collectors.toList());
        joinGroups.removeAll(collect);
    }

}
