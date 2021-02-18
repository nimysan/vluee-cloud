package com.vluee.cloud.gateway.core.rbac;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

@Slf4j
class SimpleRestResourceTest {

    @Test
    public void test() {
        String input = "/users/{username}/clients/{clientId}/roles";
        SimpleRestResource simpleRestResource = new SimpleRestResource(Arrays.asList("GET"), Arrays.asList(input));
        String s = simpleRestResource.translatePattern(input);
        Assertions.assertEquals("/users/*/clients/*/roles", s);
    }

    @Test
    public void matchTest() {
        AntPathMatcher matcher = new AntPathMatcher();
        boolean match = matcher.match("/users/*/clients/*/roles", "/users/233/clients/123/roles");
        Assertions.assertTrue(match);
        Assertions.assertFalse(matcher.match("/users/*/clients/*/roles", "/users/233/3333/clients/123/roles"));
        Assertions.assertTrue(matcher.match("/users/**/clients/**/roles", "/users/233/3333/clients/123/roles"));
    }
}