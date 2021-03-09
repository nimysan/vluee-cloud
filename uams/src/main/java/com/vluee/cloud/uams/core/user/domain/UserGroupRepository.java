package com.vluee.cloud.uams.core.user.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

public interface UserGroupRepository {

    void save(UserGroup userGroup);

    UserGroup load(AggregateId groupId);
}
