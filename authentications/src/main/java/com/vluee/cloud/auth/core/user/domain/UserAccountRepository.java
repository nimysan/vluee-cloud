package com.vluee.cloud.auth.core.user.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

import java.util.Optional;

/**
 *
 */
public interface UserAccountRepository {

    public void save(UserAccount userAccount);

    public Optional<UserAccount> loadByUsername(String username);

    public UserAccount load(AggregateId aggregateId);

}
