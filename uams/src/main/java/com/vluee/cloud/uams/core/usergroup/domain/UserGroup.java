package com.vluee.cloud.uams.core.usergroup.domain;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AggregateRoot
@Slf4j
public class UserGroup extends BaseAggregateRoot {

    public UserGroup(@NotNull AggregateId aggregateId, @NotNull String groupName) {
        this.aggregateId = aggregateId;
        this.createdTime = DateUtil.date();
        this.users = new HashSet<>(4);
        this.groupName = groupName;
    }

    private Set<AggregateId> users;

    @Getter
    private String groupName;

    @Getter
    private Date createdTime;

    public void addUser(AggregateId userId) {
        users.add(userId);
    }

    public void removeUser(AggregateId userId) {
        users.remove(userId);
    }
}
