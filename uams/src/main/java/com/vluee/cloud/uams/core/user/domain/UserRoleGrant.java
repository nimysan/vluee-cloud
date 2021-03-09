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
@Table(name = "user_role_grants")
public class UserRoleGrant {

    public UserRoleGrant(User user, CRole role) {
        userRoleGrantID = new UserRoleGrantID(user.getAggregateId(), role.getAggregateId());
        this.grantedAt = DateUtil.date();
    }

    @EmbeddedId
    private UserRoleGrantID userRoleGrantID;

    @Column
    private Date grantedAt;

    public AggregateId getRoleId(){
        return userRoleGrantID.getRoleId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleGrant that = (UserRoleGrant) o;
        return Objects.equal(userRoleGrantID, that.userRoleGrantID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userRoleGrantID);
    }

    @Data
    @AllArgsConstructor
    @ToString
    @Embeddable
    static
    class UserRoleGrantID implements Serializable {
        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "aggregateId", column = @Column(name = "user_id", nullable = false))})
        @Getter
        private AggregateId userId;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "aggregateId", column = @Column(name = "roleId", nullable = false))})
        @Getter
        private AggregateId roleId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRoleGrantID that = (UserRoleGrantID) o;
            return Objects.equal(userId, that.userId) && Objects.equal(roleId, that.roleId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(userId, roleId);
        }
    }
}
