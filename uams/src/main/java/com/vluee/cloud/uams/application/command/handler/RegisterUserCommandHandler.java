package com.vluee.cloud.uams.application.command.handler;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.RegisterUserCommand;
import com.vluee.cloud.uams.core.user.domain.User;
import com.vluee.cloud.uams.core.user.domain.UserRepository;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand, Void> {

    private final UserRepository userRepository;

    @Override
    public Void handle(RegisterUserCommand command) {
        String username = command.getUsername();
        User user = new User(AggregateId.generate(), username);
        userRepository.save(user);
        return null;
    }

}
