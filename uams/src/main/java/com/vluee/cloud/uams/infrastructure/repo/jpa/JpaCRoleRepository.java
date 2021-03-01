package com.vluee.cloud.uams.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;

import java.util.Optional;

@DomainRepositoryImpl
public class JpaCRoleRepository extends GenericJpaRepository<CRole> implements CRoleRepository {
    @Override
    public Optional<CRole> loadByName(String roleName) {
        return Optional.empty();
    }

    @Override
    public boolean existsByName(String roleName) {
        return false;
    }
}
