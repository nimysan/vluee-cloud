package com.vluee.cloud.uams.application.command.handler;

import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.GrantRoleToUserCommand;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import com.vluee.cloud.uams.core.user.domain.User;
import com.vluee.cloud.uams.core.user.domain.UserRepository;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class GrantRoleToUserCommandHandler implements CommandHandler<GrantRoleToUserCommand, Void> {

    private final UserRepository userRepository;

    private final CRoleRepository cRoleRepository;

    @Override
    public Void handle(GrantRoleToUserCommand command) {
        User load = userRepository.load(command.getUserId());
        load.grantRole(cRoleRepository.load(command.getRoleId()));
        return null;
    }
}
