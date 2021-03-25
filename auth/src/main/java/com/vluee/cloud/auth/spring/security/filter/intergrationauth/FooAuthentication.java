package com.vluee.cloud.auth.spring.security.filter.intergrationauth;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class FooAuthentication implements Authentication {

    private String authType;
    private boolean isAuthenticated = false;
    private String verificationCode;

    public FooAuthentication(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = true;
    }

    @Override
    public String getName() {
        return null;
    }
}
