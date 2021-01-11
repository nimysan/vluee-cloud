package com.vluee.cloud.uams.core.uams.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UamsUserRepository extends CrudRepository<UamsUser, String> {
    public Optional<UamsUser> findByUserCode(String userCode);
}
