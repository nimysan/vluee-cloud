package com.vluee.cloud.auth.applications.command.handler;

import com.vluee.cloud.auth.applications.command.UserAccountCreateCommand;
import com.vluee.cloud.auth.applications.service.UserRegisterService;
import com.vluee.cloud.commons.cqrs.annotations.CommandHandlerAnnotation;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import lombok.AllArgsConstructor;

@ApplicationService
@CommandHandlerAnnotation
@AllArgsConstructor
public class CreateUserAccountCommandHandler implements CommandHandler<UserAccountCreateCommand, Void> {

    private final UserRegisterService userRegisterService;

    @Override
    public Void handle(UserAccountCreateCommand command) {
        userRegisterService.createSaasUser(command.getUserId(), command.getUsername(), command.getPassword());
        return null;
    }
}
