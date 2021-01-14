package com.vluee.cloud.gateway.infrastructure;

import cn.hutool.core.convert.Convert;
import com.vluee.cloud.gateway.core.rbac.RestGrantRuleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.vluee.cloud.commons.common.rest.AuthConstant.RESOURCE_ROLES_MAP_KEY;

@Component
@AllArgsConstructor
@Slf4j
public class RedisRestGrantRuleRepository implements RestGrantRuleRepository {

    private final RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
//        redisTemplate.opsForHash().put(AuthConstant.RESOURCE_ROLES_MAP_KEY, "/saas-users/users/**", "ROLE_admin");
//        redisTemplate.opsForHash().put(AuthConstant.RESOURCE_ROLES_MAP_KEY, "/saas-users/users/**", "ROLE_super");
        redisTemplate.opsForHash().put(RESOURCE_ROLES_MAP_KEY, "* /saas-users/users/**", "admin,role,test");
        redisTemplate.opsForHash().put(RESOURCE_ROLES_MAP_KEY, "GET /saas-users/users/roles", "users-admin");
        redisTemplate.opsForHash().put(RESOURCE_ROLES_MAP_KEY, "PUT /saas-users/users/read", "users-read");
        redisTemplate.opsForHash().put(RESOURCE_ROLES_MAP_KEY, "* /saas-users/config/get", "guest");
        redisTemplate.opsForHash().put(RESOURCE_ROLES_MAP_KEY, "PUT /saas-tenants/tenants/**", "guest");
        log.info("测试规则输入成功");

        Map<Object, Object> entries = redisTemplate.opsForHash().entries(RESOURCE_ROLES_MAP_KEY);
        for (Object key : entries.keySet()) {
            String o = (String) entries.get(key);
            List<String> strings = Convert.toList(String.class, o);
            log.info("key - {} - value - {}", key, strings);
        }
    }

    @Override
    public Set<String> ruleKeys() {
        return redisTemplate.opsForHash().keys(RESOURCE_ROLES_MAP_KEY);
    }

    @Override
    public String getRoleDefinition(String key) {
        return (String) redisTemplate.opsForHash().get(RESOURCE_ROLES_MAP_KEY, key);
    }
}
