package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.GrantPermissionToRoleCommand;
import com.vluee.cloud.uams.application.command.RemoveRolePermissionCommand;
import com.vluee.cloud.uams.core.grant.GrantAction;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import lombok.AllArgsConstructor;

/**
 * 授权服务
 */
@ApplicationService
@AllArgsConstructor
public class GrantService {

    private final CRoleRepository cRoleRepository;

    public void grantPermissionToRole(GrantPermissionToRoleCommand command) {
        CRole load = cRoleRepository.load(command.getRoleId());
        //do we need to check permission id is valid or not?
        load.addPermission(command.getPermissionId());
        cRoleRepository.save(load);
    }

    public void removePermissionFromRole(RemoveRolePermissionCommand command) {
        CRole load = cRoleRepository.load(command.getRoleId());
        //do we need to check permission id is valid or not?
        load.removePermission(command.getPermissionId());
        cRoleRepository.save(load);
    }

    private GrantAction generateGrantVerbose(AggregateId permissionId, AggregateId roleId, GrantAction.GrantOperation operation) {
        //记录授权历史
        GrantAction grant = new GrantAction(AggregateId.generate(), roleId, permissionId, operation);
        return grant;
    }

}
