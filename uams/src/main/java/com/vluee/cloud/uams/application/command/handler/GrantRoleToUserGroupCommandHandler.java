package com.vluee.cloud.uams.application.command.handler;

import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.GrantRoleToUserGroupCommand;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import com.vluee.cloud.uams.core.user.domain.UserGroup;
import com.vluee.cloud.uams.core.user.domain.UserGroupRepository;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class GrantRoleToUserGroupCommandHandler implements CommandHandler<GrantRoleToUserGroupCommand, Void> {

    private final UserGroupRepository userGroupRepository;

    private final CRoleRepository cRoleRepository;

    @Override
    public Void handle(GrantRoleToUserGroupCommand command) {
        UserGroup load = userGroupRepository.load(command.getUserGroupId());
        load.grantRole(cRoleRepository.load(command.getRoleId()));
        return null;
    }
}
