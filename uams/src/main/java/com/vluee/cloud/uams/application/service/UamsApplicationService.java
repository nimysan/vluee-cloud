package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * DDD application service。
 * DDD的应用层就是一些编排脚本，真正的业务逻辑请放回到domain相关代码去。
 */
@Service
@AllArgsConstructor
@ApplicationService
public class UamsApplicationService {


    private final CRoleRepository roleRepository;

    private final RedisTemplate redisTemplate;

    @Transactional
    public void grant(Long resourceId, Long roleId) {
//        RestResourceRoleMap roleMap = new RestResourceRoleMap(resourceId, roleId);
//        restResourceRoleMapRepository.save(roleMap);
//
//        //TODO update to redis
//        RestResource resource = restResourceService.loadResource(resourceId);
//        Role role = roleService.loadRole(roleId);
//        redisTemplate.opsForHash().put(AuthConstant.RESOURCE_ROLES_MAP_KEY, resource.composeKey(), role.getName());
    }

    public String getRoles(String username, String clientId) {
        return "admin,guest";
    }


    public Iterable<CRole> listRoles() {
//        return roleService.list();
        return Collections.emptyList();
    }


    /**
     * 检查角色名称是否可用
     *
     * @param name
     * @return
     */
    public String isRoleNameAvailable(String name) {
        boolean exists = roleRepository.existsByName(name);
        if (exists) {
            return "Role name exists";
        }
        return null;
    }


}
