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
