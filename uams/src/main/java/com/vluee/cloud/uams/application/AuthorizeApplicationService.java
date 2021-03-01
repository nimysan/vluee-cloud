package com.vluee.cloud.uams.application;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.GrantPermissionToRoleCommand;
import com.vluee.cloud.uams.core.authorize.domain.CheckPermission;
import com.vluee.cloud.uams.core.authorize.exception.UamsNotManagedResourceException;
import com.vluee.cloud.uams.core.authorize.service.AuthorizeService;
import com.vluee.cloud.uams.core.permission.Grant;
import com.vluee.cloud.uams.core.permission.GrantRepository;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.permission.PermissionRepository;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@ApplicationService
@Service
@AllArgsConstructor
public class AuthorizeApplicationService {

    private final AuthorizeService authorizeService;

    private final GrantRepository grantRepository;

    private final PermissionRepository permissionRepository;

    /**
     * 给角色分配权限
     *
     * @param grantCommand
     */
    public void assginUserRole(GrantPermissionToRoleCommand grantCommand) {

    }

    /**
     * 检查用户是否拥有某个API权限
     *
     * @param userId
     * @param httpVerb
     * @param urlPattern
     * @return
     */
    public CheckPermission ownApiPermission(AggregateId userId, String httpVerb, String urlPattern) {
        User user = loadUser(userId);
        //retrieve permission
        final Permission permission = recongonizeApiPermission(httpVerb, urlPattern).orElseThrow(UamsNotManagedResourceException::new);
        return authorizeService.checkRBAC(user, permission);
    }

    /**
     * 从给定的verb和url pattern中识别permission定义
     *
     * @return
     */
    private Optional<Permission> recongonizeApiPermission(String verb, String url) {
        return StreamSupport.stream(permissionRepository.findAll().spliterator(), true).filter(t -> this.match(t, verb, url)).findFirst();
    }

    private boolean match(Permission permission, String verb, String url) {
        return true;
    }

    /**
     * 检查用户是否拥有授权
     *
     * @param userId
     * @param permissionId
     */
    public CheckPermission checkPermission(AggregateId userId, AggregateId permissionId) {
        Permission permission = permissionRepository.findById(permissionId.getLongId()).orElseThrow(RuntimeException::new);
        CheckPermission checkPermission = authorizeService.checkRBAC(loadUser(userId), permission);
        return checkPermission;
    }

    public User loadUser(AggregateId userId) {
        return new User(userId);
    }

    public Grant grant(CRole role, Permission permission) {
        return authorizeService.grant(role, permission);
    }

    public void cancelGrant(AggregateId grantId) {
        Grant grant = grantRepository.findById(grantId).orElseThrow(RuntimeException::new);
        grant.cancelGrant();
        grantRepository.save(grant);
    }
}
