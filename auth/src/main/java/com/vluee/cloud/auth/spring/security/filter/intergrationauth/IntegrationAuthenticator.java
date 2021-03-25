package com.vluee.cloud.auth.spring.security.filter.intergrationauth;

public interface IntegrationAuthenticator {

    public boolean support(FooAuthentication authentication);

    void prepare(FooAuthentication authentication);

    void complete(FooAuthentication authentication);
}
