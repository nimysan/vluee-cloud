package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.GrantPermissionToRoleCommand;
import com.vluee.cloud.uams.application.command.RemoveRolePermissionCommand;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import lombok.AllArgsConstructor;

/**
 * 授权服务
 */
@ApplicationService
@AllArgsConstructor
public class GrantService {

//    private final CRoleRepository cRoleRepository;
//
//    public void grantPermissionToRole(GrantPermissionToRoleCommand command) {
//        CRole load = cRoleRepository.load(command.getRoleId());
//        load.grantApiPermission(command.getPermissionId());
//        cRoleRepository.save(load);
//    }
//
//    public void removePermissionFromRole(RemoveRolePermissionCommand command) {
//        CRole load = cRoleRepository.load(command.getRoleId());
//        load.cancelApiPermissionGrant(command.getPermissionId());
//        cRoleRepository.save(load);
//    }
}
