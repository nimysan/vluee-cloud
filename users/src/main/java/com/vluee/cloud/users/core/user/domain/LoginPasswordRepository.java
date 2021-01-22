package com.vluee.cloud.users.core.user.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface LoginPasswordRepository extends CrudRepository<LoginPassword, Long> {

    public Optional<LoginPassword> findByUsernameAndActiveStatus(String username, boolean status);
}
