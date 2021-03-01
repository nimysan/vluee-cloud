package com.vluee.cloud.uams.core.user.domain;

import com.google.common.base.Objects;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    private Collection<AggregateId> roles = new ArrayList<>(2);

    public Collection<AggregateId> ownedRoles() {
        if (roles == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(roles);
    }

    public void addRole(@NotNull AggregateId aggregateId) {
        this.roles.add(aggregateId);
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
