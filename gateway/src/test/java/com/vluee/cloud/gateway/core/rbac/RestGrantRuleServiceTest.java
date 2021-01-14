package com.vluee.cloud.gateway.core.rbac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class RestGrantRuleServiceTest {


    @Data
    @AllArgsConstructor
    @Builder
    public static class SimpleRestResourceRequest implements RestResourceRequest {
        private HttpMethod method;
        private String url;
    }

    @Test
    public void testAllMethodMatching() {
        RestGrantRuleService restGrantRuleService = setupService("1,2", "* /users");
        RestGrantRule rule = restGrantRuleService.findRule(SimpleRestResourceRequest.builder().method(HttpMethod.GET).url("/users").build());
        Assertions.assertNotNull(rule);
        Assertions.assertTrue(2 == rule.getRoles().size());
    }

    @Test
    public void testSpecificMethodMatching() {
        RestGrantRuleService restGrantRuleService = setupService("1,2", "POST    /users");
        Assertions.assertNull(restGrantRuleService.findRule(SimpleRestResourceRequest.builder().method(HttpMethod.GET).url("/users").build()));
        Assertions.assertNotNull(restGrantRuleService.findRule(SimpleRestResourceRequest.builder().method(HttpMethod.POST).url("/users").build()));
    }

    @Test
    public void testNoRuleMatching() {
        RestGrantRuleService restGrantRuleService = setupService("1,2", "POST    /users");
        Assertions.assertNull(restGrantRuleService.findRule(SimpleRestResourceRequest.builder().method(HttpMethod.GET).url("/mock").build()));
    }

    private RestGrantRuleService setupService(String roles, String... keys) {
        RestGrantRuleService restGrantRuleService = new RestGrantRuleService(new RestGrantRuleRepository() {
            @Override
            public Set<String> ruleKeys() {
                return Arrays.asList(keys).stream().collect(Collectors.toSet());
            }

            @Override
            public String getRoleDefinition(String key) {
                return roles;
            }
        });
        return restGrantRuleService;
    }
}