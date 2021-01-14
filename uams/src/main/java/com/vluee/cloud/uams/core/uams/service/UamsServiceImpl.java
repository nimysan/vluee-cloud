package com.vluee.cloud.uams.core.uams.service;


import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.vluee.cloud.commons.common.ServiceConstants;
import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.brand.exception.BrandNotExistException;
import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.date.DateUtils;
import com.vluee.cloud.commons.common.uams.ResourceDescriptor;
import com.vluee.cloud.commons.common.uams.RestResourceDescriptor;
import com.vluee.cloud.uams.core.brand.service.Brand;
import com.vluee.cloud.uams.core.brand.service.BrandQueryService;
import com.vluee.cloud.uams.core.uams.domain.*;
import com.vluee.cloud.uams.core.uams.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UamsServiceImpl implements UamsService {

    @Autowired
    private UamsUserRepository uamsUserRepository;

    @Autowired
    private UamsRoleRepository uamsRoleRepository;

    @Autowired
    private UamsUserGroupRepository uamsUserGroupRepository;

    @Autowired
    private UamsProtectedResourceRepository uamsProtectedResourceRepository;

    @Autowired
    private UamsUserBrandMapsRepository uamsUserBrandMapsRepository;

    private final BrandQueryService saasBrandQueryService;

    private final AuditContext auditContext;

    public UamsServiceImpl(@NotNull AuditContext auditContext, @NotNull BrandQueryService brandQueryService) {
        this.auditContext = auditContext;
        this.saasBrandQueryService = brandQueryService;
    }

    @Override
    @Transactional
    public UamsUser createUser(String tenantId, String userCode) {
        UamsUser uu = new UamsUser(tenantId, userCode);
        audit(uu);
        uamsUserRepository.save(uu);
        return uu;
    }

    @Override
    @Transactional
    public UamsUserGroup createGroup(String tenantId, String groupName) {
        UamsUserGroup group = new UamsUserGroup(tenantId, groupName);
        audit(group);
        uamsUserGroupRepository.save(group);
        return group;
    }

    @Override
    @Transactional
    public Set<UamsUserGroup> getUserGroupByUser(String userId) {
        UamsUser uamsUser = uamsUserRepository.findById(userId).orElseThrow(UamsUserNotExistException::new);
        return uamsUser.getUserGroups();
    }

    @Override
    @Transactional
    public UamsUser loadUserByUserName(String username) {
        return uamsUserRepository.findByUserCode(username).orElseThrow(UamsUserNotExistException::new);
    }

    @Override
    @Transactional
    public boolean checkGrant(String userId, String resourceId) {
        UamsUser uamsUser = uamsUserRepository.findById(userId).orElseThrow(UamsUserNotExistException::new);
        UamsProtectedResource uamsProtectedResource = uamsProtectedResourceRepository.findById(resourceId).orElseThrow(UamsProtectedResourceNotExistException::new);
        //TODO n+1 SQL问题，需要优化
        return uamsUser.getRoleKeys().stream().flatMap(t -> uamsRoleRepository.findById(t).orElseThrow(UamsRoleNotExistException::new).getAllResourceKeys(ServiceConstants.UamsPermissionType.API).stream()).anyMatch(t -> resourceId.equalsIgnoreCase(t));
    }

    @Override
    @Transactional
    public UamsUserGroup createGroup(String tenantId, String groupName, String brandId) {
        UamsUserGroup group = new UamsUserGroup(tenantId, groupName, brandId);
        audit(group);
        uamsUserGroupRepository.save(group);
        return group;
    }

    @Override
    @Transactional
    public UamsRole createRole(String tenantId, String roleName) {
        UamsRole role = new UamsRole(tenantId, roleName);
        audit(role);
        uamsRoleRepository.save(role);
        return role;
    }

    @Override
    @Transactional
    public UamsRole createRole(String tenantId, String roleName, @NotNull String brandId) {
        Brand brand = saasBrandQueryService.getBrand(brandId).orElseThrow(BrandNotExistException::new);
        Assert.isTrue(brand.getBrandId().equals(brandId), "品牌与租户不符");
        UamsRole role = new UamsRole(tenantId, roleName, brandId);
        audit(role);
        uamsRoleRepository.save(role);
        return role;
    }


    @Override
    @Transactional
    public void assignUserToGroup(String userId, String groupId) {
        UamsUser uamsUser = uamsUserRepository.findById(userId).orElseThrow(UamsUserNotExistException::new);
        UamsUserGroup uamsUserGroup = uamsUserGroupRepository.findById(groupId).orElseThrow(UamsGroupNotExistException::new);
        checkBrandMatched(uamsUserGroup, uamsUser);
        uamsUserGroup.addUser(uamsUser);
        uamsUser.addGroup(uamsUserGroup);
        uamsUserGroupRepository.save(uamsUserGroup);
    }

    @Override
    @Transactional
    public void assignRoleToGroup(String groupId, String roleId) {
        UamsRole uamsRole = uamsRoleRepository.findById(roleId).orElseThrow(UamsRoleNotExistException::new);
        UamsUserGroup uamsUserGroup = uamsUserGroupRepository.findById(groupId).orElseThrow(UamsGroupNotExistException::new);
        checkBrandMatched(uamsRole, uamsUserGroup);
        uamsUserGroup.addRole(uamsRole);
        uamsUserGroupRepository.save(uamsUserGroup);
    }

    @Override
    @Transactional
    public Collection<UamsRole> getRolesByUser(String userId) {
        UamsUser uamsUser = uamsUserRepository.findById(userId).orElseThrow(UamsUserNotExistException::new);
        Set<UamsRole> merge = new LinkedHashSet<>();
        //读取所有的直接关联属性
        merge.addAll(uamsUser.getDirectRoles());
        //读取用户组关联的属性
        uamsUser.getUserGroups().stream().forEach(t -> {
            merge.addAll(t.getRoles());
        });
        return merge;
    }

    @Override
    @Transactional
    public void assignRoleToUser(String userId, String roleId) {
        UamsRole uamsRole = uamsRoleRepository.findById(roleId).orElseThrow(UamsRoleNotExistException::new);
        UamsUser uamsUser = uamsUserRepository.findById(userId).orElseThrow(UamsUserNotExistException::new);
        checkBrandMatched(uamsRole, uamsUser);
        uamsUser.addRole(uamsRole);
    }

    /**
     * @param brandResource
     * @param brandManager
     */
    private void checkBrandMatched(@NotNull BrandResource brandResource, @NotNull BrandOwner brandManager) {
        //修改写代码的方式 TODO
        if (!brandResource.isOwnByBrand()) {
            return;
        } // 品牌特有资源

        if (brandManager.isOwnBrand(brandResource.getBrandId())) {
            return;
        } // 品牌管理者拥有该品牌

        throw new UamsInvalidOperationException();
    }

    /**
     * 如果用户拥有所有品牌, 返回真
     * 否则对比用户拥有的品牌和角色所属品牌对应关系
     *
     * @param user
     * @param brandId
     * @return
     */
    private boolean checkUserOwnBrand(@NotNull UamsUser user, @NotNull String brandId) {
        if (user.isOwnAllBrands()) {
            return true;
        }
        Iterable<String> brandByUser = this.getBrandByUser(user.getId());
        return StreamSupport.stream(brandByUser.spliterator(), true).anyMatch(t -> t.equals(brandId));
    }

    @Override
    @Transactional
    public UamsProtectedResource createProtectedResource(final ResourceDescriptor descriptor, final String contributor) {
        UamsProtectedResource protectedResource = new UamsProtectedResource(descriptor.getName(), descriptor.getDescription(), contributor, descriptor);
        audit(protectedResource);
        uamsProtectedResourceRepository.save(protectedResource);
        return protectedResource;
    }

    @Override
    @Transactional
    public void assignResourceToRole(String resourceId, String roleId) {
        UamsProtectedResource uamsProtectedResource = uamsProtectedResourceRepository.findById(resourceId).orElseThrow(UamsProtectedResourceNotExistException::new);
        final UamsRole uamsRole = uamsRoleRepository.findById(roleId).orElseThrow(UamsRoleNotExistException::new);
        uamsRole.addResource(uamsProtectedResource);
        auditModify(uamsRole);
        uamsRoleRepository.save(uamsRole);
    }

    @Override
    @Transactional
    public Map<String, ResourceDescriptor> listResources() {
        java.util.Map<String, ResourceDescriptor> map = new HashMap<>();
        StreamSupport.stream(uamsProtectedResourceRepository.findAll().spliterator(), true).forEach(t -> {
            map.put(t.getId(), this.resourceDescriptor(t));
        });
        return map;
    }

    @Override
    @Transactional
    public UamsRole loadRole(String id) {
        return uamsRoleRepository.findById(id).orElseThrow(UamsRoleNotExistException::new);
    }

    @Override
    @Transactional
    public void assignUserToBrand(@NotNull String brandId, @NotNull String userId) {
        UamsUser uamsUser = uamsUserRepository.findById(userId).orElseThrow(UamsUserNotExistException::new);
        Brand brand = saasBrandQueryService.getBrand(brandId).orElseThrow(BrandNotExistException::new);
        UamsUserBrandMaps userBrandMap = new UamsUserBrandMaps(uamsUser.getId(), brand.getBrandId());
        audit(userBrandMap);
        uamsUserBrandMapsRepository.save(userBrandMap);
    }

    @Override
    public Iterable<String> getBrandByUser(@NotNull String userId) {
        Iterable<UamsUserBrandMaps> uamsUserBrands = uamsUserBrandMapsRepository.findByIdUserId(userId);
        return StreamSupport.stream(uamsUserBrands.spliterator(), true).map(t -> t.getBrandId()).collect(Collectors.toList());
    }

    @Override
    public void setOwnAllBrands(String userId) {
        UamsUser uamsUser = uamsUserRepository.findById(userId).orElseThrow(UamsUserNotExistException::new);
        if (uamsUser.isOwnAllBrands()) {
            return;
        }
        uamsUser.setOwnAllBrands();
        auditModify(uamsUser);
        uamsUserRepository.save(uamsUser);
    }

    private ResourceDescriptor resourceDescriptor(UamsProtectedResource resource) {
        return JSONUtil.toBean(resource.getResourceDescriptorInJsonStr(), RestResourceDescriptor.class);
    }

    private void audit(AuditAware auditAware) {
        auditAware.setCreatedBy(auditContext.getOpId());
        auditAware.setCreatedAt(DateUtils.currentDate());
    }

    private void auditModify(AuditAware auditAware) {
        auditAware.setLastModifiedBy(auditContext.getOpId());
        auditAware.setLastModifiedAt(DateUtils.currentDate());
    }

}
