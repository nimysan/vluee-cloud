package com.vluee.cloud.uams.core.user.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

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

    @Enumerated
    public UserGroupStatus status;

    enum UserGroupStatus {
        ENABLE, DISABLED
    }
}
