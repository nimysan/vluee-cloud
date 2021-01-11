package com.vluee.cloud.gateway.spring.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ClaimAuthorityJwtGrantedAuthoritiesConverterTest {

    private ClaimAuthorityJwtGrantedAuthoritiesConverter converter;

    @BeforeEach
    public void setup() {
        converter = new ClaimAuthorityJwtGrantedAuthoritiesConverter();
    }

    @Test
    public void testRoleToAuth() {
        String mockRole = "manager";
        Collection<GrantedAuthority> grantedAuthorities = converter.convert(mockJwt(mockRole));

        assertNotNull(grantedAuthorities);
        assertTrue(grantedAuthorities.size() > 0);

        assertTrue(grantedAuthorities.contains(new SimpleGrantedAuthority("task_create")));
        assertTrue(grantedAuthorities.contains(new SimpleGrantedAuthority("task_delete")));
        assertFalse(grantedAuthorities.contains(new SimpleGrantedAuthority("task_not_exist")));
    }

    private Jwt mockJwt(@NotNull String... tokenAuthorities) {
        return Jwt.withTokenValue("test").header("test", "123").claim("authorities", Arrays.asList(tokenAuthorities)).build();
    }
}