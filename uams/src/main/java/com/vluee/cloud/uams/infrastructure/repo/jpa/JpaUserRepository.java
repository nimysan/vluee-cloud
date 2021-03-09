package com.vluee.cloud.uams.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.uams.core.user.domain.User;
import com.vluee.cloud.uams.core.user.domain.UserRepository;

@DomainRepositoryImpl
public class JpaUserRepository extends GenericJpaRepository<User> implements UserRepository {
}
