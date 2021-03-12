package com.vluee.cloud.uams.application.listeners;

import com.vluee.cloud.commons.ddd.annotations.event.EventListener;
import com.vluee.cloud.commons.ddd.annotations.event.EventListeners;
import com.vluee.cloud.uams.core.user.domain.User;
import com.vluee.cloud.uams.core.user.domain.UserRepository;
import com.vluee.cloud.uams.core.user.domain.events.UserRegisterEvent;
import com.vluee.cloud.uams.interfaces.feign.AccountFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EventListeners
@AllArgsConstructor
@Slf4j
public class UserRegisterEventListener {

    public final AccountFacade accountFacade;

    private final UserRepository userRepository;

    @EventListener
    public void handleEvent(UserRegisterEvent userRegisterEvent) {
        // call authentication service to create account
        User user = userRepository.load(userRegisterEvent.getUserId());
        accountFacade.createAccount(user.getAggregateId().getId(), user.getUsername(), userRegisterEvent.getPassword());
    }

}
