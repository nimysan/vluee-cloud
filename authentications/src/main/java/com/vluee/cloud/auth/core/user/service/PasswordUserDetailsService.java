package com.vluee.cloud.auth.core.user.service;

import com.vluee.cloud.auth.core.user.domain.UserAccount;
import com.vluee.cloud.auth.core.user.domain.UserAccountRepository;
import com.vluee.cloud.commons.ddd.annotations.domain.DomainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.function.Supplier;

/**
 * 认证服务
 */
@DomainService
@Slf4j
@AllArgsConstructor
public class PasswordUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserAccount userAccount = userAccountRepository.loadByUsername(username).orElseThrow((Supplier<RuntimeException>) () -> new UsernameNotFoundException("Failed to load user with username " + username));
            return new User(username, userAccount.getLoginPassword().getActivePassword(), true, true, true, true, Collections.emptyList());
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to load user with username " + username, e);
        }
    }
}
