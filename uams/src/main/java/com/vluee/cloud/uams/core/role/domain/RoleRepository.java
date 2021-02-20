package com.vluee.cloud.uams.core.role.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> loadByName(String roleName);

    boolean existsByName(String roleName);
}
