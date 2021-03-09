package com.vluee.cloud.uams.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.uams.core.user.domain.UserGroup;
import com.vluee.cloud.uams.core.user.domain.UserGroupRepository;

@DomainRepositoryImpl
public class JpaUserGroupRepository extends GenericJpaRepository<UserGroup> implements UserGroupRepository {
}
