package com.vluee.cloud.gateway.core.rbac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class SimpleRestResource {

    private static final String[] ignoreList = {"/v2/api-docs", "/v3/api-docs", "/swagger-resources/**", "/actuator/**", "/error"};

    private List<String> methods;
    private List<String> urls;

    public List<RestResourceForGrant> convertForGrants() {
        List<RestResourceForGrant> forGrants = new ArrayList<>();
        if (urls == null || urls.size() == 0) {
            return Collections.emptyList();
        }
        urls.stream().filter(t -> !isIgnore(t)).map(this::translatePattern).forEach(t -> {
            if (methods == null || methods.size() == 0) {
                RestResourceForGrant build = RestResourceForGrant.builder().method("*").pattern(t).build();
                forGrants.add(build);
            } else {
                methods.forEach(m -> {
                    forGrants.add(RestResourceForGrant.builder().method(m).pattern(t).build());
                });

            }
        });
        return forGrants;
    }

    String translatePattern(String input) {
        return Arrays.stream(input.split("/")).map(t -> {
            if (t.startsWith("{") && t.endsWith("}")) {
                return "*";
            } else {
                return t;
            }
        }).collect(Collectors.joining("/"));
    }

    private boolean isIgnore(String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return Arrays.stream(ignoreList).filter(t -> matcher.match(t, url)).findAny().isPresent();
    }
}
