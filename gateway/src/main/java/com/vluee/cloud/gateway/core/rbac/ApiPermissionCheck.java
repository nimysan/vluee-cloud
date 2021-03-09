package com.vluee.cloud.gateway.core.rbac;

import com.vluee.cloud.commons.common.string.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class ApiPermissionCheck {

    private final RedisTemplate redisTemplate;

    public List<String> getRoleList(String userKey) {
        String user_roles = (String) redisTemplate.opsForHash().get("user_roles", userKey);
        return Arrays.asList(user_roles.split(","));
    }

    public boolean hasPermission(String roleId, String apiKey) {
        String role_apis = (String) redisTemplate.opsForHash().get("api_roles", apiKey);
        if (StringUtils.isNotEmpty(role_apis)) {
            return Arrays.stream(role_apis.split(",")).anyMatch(roleId::equalsIgnoreCase);
        }
        return false;
    }
}
