package com.vluee.cloud.uams.application.command.handler;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.annotations.CommandHandlerAnnotation;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.GrantPermissionToRoleCommand;
import com.vluee.cloud.uams.core.permission.domain.ApiPermissionRepository;
import com.vluee.cloud.uams.core.permission.domain.PermissionType;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationService
@CommandHandlerAnnotation
public class GrantPermissionToRoleCommandHandler implements CommandHandler<GrantPermissionToRoleCommand, Void> {

    private final CRoleRepository roleRepository;
    private final ApiPermissionRepository apiPermissionRepository;

    @Override
    public Void handle(GrantPermissionToRoleCommand command) {
        AggregateId roleId = command.getRoleId();
        CRole role = roleRepository.load(roleId);
        if (PermissionType.API.equals(command.getPermissionType())) {
            role.grantApiPermission(apiPermissionRepository.load(command.getPermissionId()));
        }
        return null;
    }
}
