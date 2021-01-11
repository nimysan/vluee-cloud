package com.vluee.cloud.uams.core.uams.service;

import com.vluee.cloud.commons.common.uams.ResourceDescriptor;
import com.vluee.cloud.uams.core.uams.domain.UamsProtectedResource;
import com.vluee.cloud.uams.core.uams.domain.UamsRole;
import com.vluee.cloud.uams.core.uams.domain.UamsUser;
import com.vluee.cloud.uams.core.uams.domain.UamsUserGroup;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 用户/用户组/角色/资源 管理服务
 * <p>
 * Domain Service
 */
public interface UamsService {

    /**
     * 创建用户
     *
     * @param tenantId
     * @param userCode
     * @return
     */
    UamsUser createUser(String tenantId, String userCode);

    /**
     * 创建用户组
     *
     * @param tenantId
     * @param groupName
     * @return
     */
    UamsUserGroup createGroup(String tenantId, String groupName);

    /**
     * 创建品牌特定用户组
     *
     * @param tenantId
     * @param groupName
     * @param brandId
     * @return
     */
    UamsUserGroup createGroup(String tenantId, String groupName, String brandId);

    /**
     * 创建角色
     *
     * @param tenantId
     * @param roleName
     * @return
     */
    UamsRole createRole(String tenantId, String roleName);

    /**
     * 创建品牌特定角色
     *
     * @param tenantId
     * @param roleName
     * @param brandId
     * @return
     */
    UamsRole createRole(String tenantId, String roleName, String brandId);

    /**
     * 将用户划入用户组
     *
     * @param userId
     * @param groupId
     */
    void assignUserToGroup(String userId, String groupId);

    /**
     * 给用户组分配角色
     *
     * @param groupId
     * @param roleId
     */
    void assignRoleToGroup(String groupId, String roleId);

    /**
     * 获取用户的所有角色
     *
     * @param userId
     * @return
     */
    Collection<UamsRole> getRolesByUser(String userId);

    /**
     * 直接给用户分配角色
     *
     * @param userId
     * @param roleId
     */
    void assignRoleToUser(String userId, String roleId);

    /**
     * 创建资源  资源就是被相关权限保护的对象
     *
     * @param descriptor
     * @param contributor
     * @return
     */
    UamsProtectedResource createProtectedResource(ResourceDescriptor descriptor, String contributor);

    /**
     * 给角色分配资源
     *
     * @param resourceId
     * @param roleId
     */
    void assignResourceToRole(String resourceId, String roleId);

    /**
     * 获取所有系统管理的资源
     *
     * @return
     */
    Map<String, ResourceDescriptor> listResources();

    /**
     * 加载角色
     *
     * @param id
     * @return
     */
    UamsRole loadRole(String id);

    /**
     * 分配用户给品牌
     *
     * @param brandId
     * @param userId
     */
    void assignUserToBrand(String brandId, String userId);

    /**
     * 获取用户关联的所有品牌
     *
     * @param userId
     * @return
     */
    Iterable<String> getBrandByUser(String userId);

    /**
     * 设置用户是否具有管理所有品牌
     *
     * @param userId
     */
    void setOwnAllBrands(String userId);

    /**
     * 获取用户所属的用户组
     *
     * @param userId
     * @return
     */
    Set<UamsUserGroup> getUserGroupByUser(String userId);

    /**
     * 获取用户详情
     *
     * @param username
     * @return
     */
    UamsUser loadUserByUserName(String username);

    boolean checkGrant(@NotNull String userId, @NotNull String resourceId);
}
