package com.vluee.cloud.auth.core.client.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, String>, JpaSpecificationExecutor<OauthClientDetails> {

}