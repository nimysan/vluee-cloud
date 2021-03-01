package com.vluee.cloud.uams.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.permission.PermissionRepository;

@DomainRepositoryImpl
public class JpaPermissionRepository extends GenericJpaRepository<Permission> implements PermissionRepository {
}
