package com.vluee.cloud.uams.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.uams.core.permission.domain.ApiPermission;
import com.vluee.cloud.uams.core.permission.domain.ApiPermissionRepository;

@DomainRepositoryImpl
public class JpaApiPermissionRepository extends GenericJpaRepository<ApiPermission> implements ApiPermissionRepository {
}
