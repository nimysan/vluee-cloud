package com.vluee.cloud.uams.application.command.handler;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.commons.ddd.support.event.DomainEventPublisherFactory;
import com.vluee.cloud.uams.application.command.RegisterUserCommand;
import com.vluee.cloud.uams.core.user.domain.User;
import com.vluee.cloud.uams.core.user.domain.UserRepository;
import com.vluee.cloud.uams.core.user.domain.events.UserRegisterEvent;
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
        DomainEventPublisherFactory.getPublisher().publish(new UserRegisterEvent(user.getAggregateId(), command.getPassword())); //TODO 是否是最好得方式？
        return null;
    }

}
