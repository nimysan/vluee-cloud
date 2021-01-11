package com.vluee.cloud.uams.core.uams.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UamsUserBrandMapsRepository extends JpaRepository<UamsUserBrandMaps, UamsUserBrandMaps.UamsUserBrandKey>, JpaSpecificationExecutor<UamsUserBrandMaps> {
    public Iterable<UamsUserBrandMaps> findByIdUserId(String userId);
}