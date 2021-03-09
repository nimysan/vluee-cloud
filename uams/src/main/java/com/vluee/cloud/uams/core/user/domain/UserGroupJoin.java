package com.vluee.cloud.uams.core.user.domain;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.Objects;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_group_joins")
@NoArgsConstructor
public class UserGroupJoin {

    public UserGroupJoin(User user, UserGroup userGroup) {
        this.userGroupJoinID = new UserGroupJoinID(user.getAggregateId(), userGroup.getAggregateId());
        this.joinAt = DateUtil.date();
        this.removed = false;
    }

    @Id
    @EmbeddedId
    private UserGroupJoinID userGroupJoinID;

    public AggregateId getGroupId() {
        return userGroupJoinID.getUserGroupId();
    }

    /**
     * 入组时间
     */
    @Column
    private Date joinAt;


    @Embeddable
    @AllArgsConstructor
    public class UserGroupJoinID implements Serializable {
        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "aggregateId", column = @Column(name = "user_id", nullable = false))})
        @Getter
        private AggregateId userId;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "aggregateId", column = @Column(name = "user_group_id", nullable = false))})
        @Getter
        private AggregateId userGroupId;
    }

    @Column
    @Getter
    private boolean removed = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupJoin that = (UserGroupJoin) o;
        return Objects.equal(userGroupJoinID, that.userGroupJoinID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userGroupJoinID);
    }
}
