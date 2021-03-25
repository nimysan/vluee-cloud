package com.vluee.cloud.auth.spring.security.filter.intergrationauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VerificationCodeAuthenticator implements IntegrationAuthenticator {
    @Override
    public boolean support(FooAuthentication authentication) {
        return true; //TODO
    }

    @Override
    public void prepare(FooAuthentication authentication) {
        //do nothing
    }

    @Override
    public void complete(FooAuthentication authentication) {
        authentication.setAuthenticated(true);
        log.info("Completed the authentication {}", authentication.getName());
    }
}
