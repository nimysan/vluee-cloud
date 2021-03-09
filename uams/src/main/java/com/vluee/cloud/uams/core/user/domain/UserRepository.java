package com.vluee.cloud.uams.core.user.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

public interface UserRepository {
    void save(User user);

    User load(AggregateId userId);
}
