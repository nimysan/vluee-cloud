package com.vluee.cloud.uams.application.command.handler;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.CreateUserGroupCommand;
import com.vluee.cloud.uams.core.user.domain.UserGroup;
import com.vluee.cloud.uams.core.user.domain.UserGroupRepository;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class CreateUserGroupHandler implements CommandHandler<CreateUserGroupCommand, Void> {

    private final UserGroupRepository userGroupRepository;

    @Override
    public Void handle(CreateUserGroupCommand command) {
        String groupName = command.getGroupName();
        UserGroup userGroup = new UserGroup(AggregateId.generate(), groupName);
        userGroupRepository.save(userGroup);
        return null;
    }

}
