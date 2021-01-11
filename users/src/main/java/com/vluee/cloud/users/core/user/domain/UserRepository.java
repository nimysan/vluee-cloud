package com.vluee.cloud.users.core.user.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    public Optional<User> findByUserName(String userName);
}
