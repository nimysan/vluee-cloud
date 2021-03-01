package com.vluee.cloud.uams.core.role.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<CRole, AggregateId> {

    Optional<CRole> loadByName(String roleName);

    boolean existsByName(String roleName);
}
