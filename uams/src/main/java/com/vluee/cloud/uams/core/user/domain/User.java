package com.vluee.cloud.uams.core.user.domain;

import com.google.common.base.Objects;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * 聚合之间通过id引用
 */
@AggregateRoot
public class User {
    @Getter
    private AggregateId id;

    public User(AggregateId id) {
        this.id = id;
    }

    private Collection<AggregateId> roles;

    public Collection<AggregateId> ownedRoles() {
        return Collections.unmodifiableCollection(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
