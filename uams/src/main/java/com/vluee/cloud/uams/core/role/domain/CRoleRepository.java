package com.vluee.cloud.uams.core.role.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

import java.util.Optional;

public interface CRoleRepository {

    void save(CRole role);

    CRole load(AggregateId roleId);

    Optional<CRole> loadByName(String roleName);

    boolean existsByName(String roleName);
}
