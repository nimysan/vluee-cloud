package com.vluee.cloud.uams.application.listeners;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.common.rest.AuthConstant;
import com.vluee.cloud.commons.common.string.StringUtils;
import com.vluee.cloud.uams.core.permission.domain.ApiPermission;
import com.vluee.cloud.uams.core.permission.domain.ApiPermissionRepository;
import com.vluee.cloud.uams.core.resources.domain.ApiResource;
import com.vluee.cloud.uams.core.resources.domain.ApiResourceRepository;
import com.vluee.cloud.uams.core.role.domain.events.RolePermissionAddedEvent;
import com.vluee.cloud.uams.core.user.domain.User;
import com.vluee.cloud.uams.core.user.domain.UserRepository;
import com.vluee.cloud.uams.core.user.domain.events.UserRoleGrantedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class ApiPermissionRedisCacheListener {

    private final RedisTemplate redisTemplate;
    private final ApiPermissionRepository apiPermissionRepository;
    private final ApiResourceRepository apiResourceRepository;
    private final UserRepository userRepository;

    /**
     * 刷新所有的api-roles对应关系
     */
    public void refreshApiRoles() {
        //TODO
    }

    /**
     * 刷新redis缓存
     *
     * @param userRoleGrantedEvent
     */
    public void userRole(UserRoleGrantedEvent userRoleGrantedEvent) {
        String roleId = userRoleGrantedEvent.getRoleId().getId();
        String userId = userRoleGrantedEvent.getUserId().getId();
        User load = userRepository.load(new AggregateId(userId));
        String valueString = (String) redisTemplate.opsForHash().get(AuthConstant.USER_ROLES_MAP_KEY, load.getUsername());//username as key instead of the user id
        redisTemplate.opsForHash().put(AuthConstant.USER_ROLES_MAP_KEY, userId, appendWithComma(valueString, roleId));
    }

    public void rolePermission(RolePermissionAddedEvent rolePermissionAddedEvent) {
        String roleId = rolePermissionAddedEvent.getRoleId().getId();
        String permissionId = rolePermissionAddedEvent.getPermissionId().getId();

        ApiPermission apiPermission = apiPermissionRepository.load(rolePermissionAddedEvent.getPermissionId());
        ApiResource apiResource = apiResourceRepository.load(apiPermission.getResourceId());
        String apiKey = AuthConstant.apiCacheKey(apiResource.getRestApi().getVerb(), apiResource.getRestApi().getUrl());

        String valueString = (String) redisTemplate.opsForHash().get(AuthConstant.API_ROLES_MAP_KEY, apiKey);
        redisTemplate.opsForHash().put(AuthConstant.API_ROLES_MAP_KEY, apiKey, appendWithComma(valueString, roleId));
    }

    private String appendWithComma(String source, String appendix) {
        if (StringUtils.isNotEmpty(source)) {
            List<String> strings = Arrays.asList(source.split(","));
            if (!strings.contains(appendix)) {
                return new StringBuilder().append(source).append(",").append(appendix).toString();
            }
            return source;
        } else {
            return appendix;
        }
    }
}
