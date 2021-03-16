package com.vluee.cloud.auth.infrastructure.repo.jpa;

import com.vluee.cloud.auth.core.user.domain.UserAccount;
import com.vluee.cloud.auth.core.user.domain.UserAccountRepository;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;

import javax.persistence.Query;
import java.util.Optional;

@DomainRepositoryImpl
public class JpaUserAccountRepository extends GenericJpaRepository<UserAccount> implements UserAccountRepository {
    @Override
    public Optional<UserAccount> loadByUsername(String username) {
        Query query = entityManager.createQuery("from UserAccount su where su.userData.username=:username");
        query.setParameter("username", username);
        UserAccount singleResult = (UserAccount) query.getSingleResult();
        return Optional.of(singleResult);
    }
}
