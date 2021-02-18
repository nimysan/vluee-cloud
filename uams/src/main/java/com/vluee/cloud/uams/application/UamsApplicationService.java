package com.vluee.cloud.uams.application;

import com.vluee.cloud.commons.common.rest.AuthConstant;
import com.vluee.cloud.uams.core.resource.domain.RestResource;
import com.vluee.cloud.uams.core.resource.domain.RestResourceRoleMap;
import com.vluee.cloud.uams.core.resource.domain.RestResourceRoleMapRepository;
import com.vluee.cloud.uams.core.resource.service.RestResourceService;
import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.role.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DDD application service
 */
@Service
@AllArgsConstructor
public class UamsApplicationService {

    private final RestResourceRoleMapRepository restResourceRoleMapRepository;

    private final RestResourceService restResourceService;

    private final RoleService roleService;

    private final RedisTemplate redisTemplate;

    @Transactional
    public void grant(Long resourceId, Long roleId) {
        RestResourceRoleMap roleMap = new RestResourceRoleMap(resourceId, roleId);
        restResourceRoleMapRepository.save(roleMap);

        //TODO update to redis
        RestResource resource = restResourceService.loadResource(resourceId);
        Role role = roleService.loadRole(roleId);
        redisTemplate.opsForHash().put(AuthConstant.RESOURCE_ROLES_MAP_KEY, resource.composeKey(), role.getName());
    }

    public String getRoles(String username, String clientId) {
        return "admin,guest";
    }


    public Iterable<Role> listRoles() {
        return roleService.list();
    }
}
