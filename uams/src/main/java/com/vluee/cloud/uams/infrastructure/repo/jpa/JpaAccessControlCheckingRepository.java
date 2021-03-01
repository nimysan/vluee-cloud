package com.vluee.cloud.uams.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.uams.core.rbac.domain.AccessControlChecking;
import com.vluee.cloud.uams.core.rbac.domain.AccessControlCheckingRepository;

@DomainRepositoryImpl
public class JpaAccessControlCheckingRepository extends GenericJpaRepository<AccessControlChecking> implements AccessControlCheckingRepository {
}
