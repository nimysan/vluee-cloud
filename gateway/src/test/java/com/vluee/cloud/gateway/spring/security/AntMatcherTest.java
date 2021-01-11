package com.vluee.cloud.gateway.spring.security;

import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AntMatcherTest {


    private AntPathMatcher matcher = new AntPathMatcher();

    @Test
    public void test() {
        assertTrue(matcher.match("/*.js", "/test.js"));
        assertTrue(matcher.match("/webjars/**", "/webjars/js/chunk-vendors.9ff0f717.js"));
        assertTrue(matcher.match("/swagger-resources/**", "/swagger-resources/js/chunk-vendors.9ff0f717.js"));
        assertTrue(matcher.match("/swagger-resources/**", "/swagger-resources"));

        assertTrue(matcher.match("/**/v2/api-docs", "/aistore-monitor/test/v2/api-docs"));

        assertTrue(matcher.match("/*/v2/api-docs", "/aistore-monitor/v2/api-docs"));
    }
}
