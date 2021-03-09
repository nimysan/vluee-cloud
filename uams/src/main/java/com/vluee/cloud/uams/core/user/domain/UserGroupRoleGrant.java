package com.vluee.cloud.uams.core.user.domain;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.Objects;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.core.role.domain.CRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usergroup_role_grants")
public class UserGroupRoleGrant {

    public UserGroupRoleGrant(UserGroup userGroup, CRole role) {
        userGroupRoleGrantID = new UserGroupRoleGrantID(userGroup.getAggregateId(), role.getAggregateId());
        grantedAt = DateUtil.date();
    }

    @EmbeddedId
    private UserGroupRoleGrantID userGroupRoleGrantID;

    @Column
    private Date grantedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroupRoleGrant)) return false;
        UserGroupRoleGrant that = (UserGroupRoleGrant) o;
        return Objects.equal(userGroupRoleGrantID, that.userGroupRoleGrantID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userGroupRoleGrantID);
    }

    @Data
    @AllArgsConstructor
    @ToString
    @Embeddable
    class UserGroupRoleGrantID implements Serializable {
        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "aggregateId", column = @Column(name = "user_group_id", nullable = false))})
        @Getter
        private AggregateId userGroupId;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "aggregateId", column = @Column(name = "roleId", nullable = false))})
        @Getter
        private AggregateId roleId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserGroupRoleGrantID)) return false;
            UserGroupRoleGrantID that = (UserGroupRoleGrantID) o;
            return Objects.equal(userGroupId, that.userGroupId) && Objects.equal(roleId, that.roleId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(userGroupId, roleId);
        }
    }
}
