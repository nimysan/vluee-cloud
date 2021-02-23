package com.vluee.cloud.uams.application;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.GrantPermissionToRoleCommand;
import com.vluee.cloud.uams.core.authorize.AuthorizeService;
import com.vluee.cloud.uams.core.authorize.domain.CheckPermission;
import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.permission.PermissionRepository;
import com.vluee.cloud.uams.core.role.domain.Role;
import com.vluee.cloud.uams.core.role.domain.RoleRepository;
import com.vluee.cloud.uams.core.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@ApplicationService
@Service
@AllArgsConstructor
public class AuthorizeApplicationService {

    private final AuthorizeService authorizeService;

    private final PermissionRepository permissionRepository;

    private final RoleRepository roleRepository;

    /**
     * 给角色分配权限
     *
     * @param grantCommand
     */
    public void assginUserRole(GrantPermissionToRoleCommand grantCommand) {

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

    public void grantPermissionToRole(AggregateId roleId, AggregateId permissionId) {
        Role role = roleRepository.findById(roleId.getLongId()).orElseThrow(RuntimeException::new);
        Permission permission = permissionRepository.findById(permissionId.getLongId()).orElseThrow(RuntimeException::new);
        role.grantPermission(permission);
        roleRepository.save(role);
    }
}
