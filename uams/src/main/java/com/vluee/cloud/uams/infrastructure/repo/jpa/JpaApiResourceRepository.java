package com.vluee.cloud.uams.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.uams.core.permission.ApiResource;
import com.vluee.cloud.uams.core.permission.ApiResourceRepository;

@DomainRepositoryImpl
public class JpaApiResourceRepository extends GenericJpaRepository<ApiResource> implements ApiResourceRepository {
}
