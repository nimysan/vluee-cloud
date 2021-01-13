package com.vluee.cloud.gateway.core.rbac;

import cn.hutool.core.convert.Convert;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Set;

@Service
@AllArgsConstructor
public class RestGrantRuleService {

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final RestGrantRuleRepository restGrantRuleRepository;

    public RestGrantRule findRule(RestResourceRequest resource) {
        Set<String> keys = restGrantRuleRepository.ruleKeys();
        for (String stringKey : keys) {
            String[] split = stringKey.split("\\s+");
            if (split != null && split.length == 2) {
                String method = split[0];
                String url = split[1];
                boolean match = matchExchange(url, method, resource);
                if (match) {
                    String roleDefinition = restGrantRuleRepository.getRoleDefinition(stringKey);
                    return RestGrantRule.builder().url(url).method(method).roles(Convert.toList(String.class, roleDefinition)).build();
                }
            }
        }
        return null;
    }

    private boolean matchAllMethod(String method) {
        return "*".equalsIgnoreCase(method);
    }

    /**
     * 验证该规则是否能够匹配给定得请求 （验证请求方法和url路径)
     *
     * @param exchange
     * @return
     */
    public boolean matchExchange(String url, String ruleMethod, RestResourceRequest exchange) {
        if (this.matchMethod(ruleMethod, exchange)) {
            return pathMatcher.match(url, exchange.getUrl());
        }
        return false;
    }

    private boolean matchMethod(String ruleMethod, RestResourceRequest exchange) {
        if (!matchAllMethod(ruleMethod)) {
            HttpMethod method = exchange.getMethod();
            return method.matches(ruleMethod);
        } else {
            return true;
        }
    }
}
