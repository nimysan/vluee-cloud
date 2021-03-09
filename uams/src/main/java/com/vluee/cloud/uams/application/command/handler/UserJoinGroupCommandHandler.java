package com.vluee.cloud.uams.application.command.handler;

import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.UserJoinGroupCommand;
import com.vluee.cloud.uams.core.user.domain.User;
import com.vluee.cloud.uams.core.user.domain.UserGroupRepository;
import com.vluee.cloud.uams.core.user.domain.UserRepository;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class UserJoinGroupCommandHandler implements CommandHandler<UserJoinGroupCommand, Void> {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    @Override
    public Void handle(UserJoinGroupCommand command) {
        User user = userRepository.load(command.getUserId());
        user.joinGroup(userGroupRepository.load(command.getUserGroupId()));
        return null;
    }

}
