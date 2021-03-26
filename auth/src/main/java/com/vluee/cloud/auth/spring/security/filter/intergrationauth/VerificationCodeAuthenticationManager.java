package com.vluee.cloud.auth.spring.security.filter.intergrationauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;


@Slf4j
public class VerificationCodeAuthenticationManager implements AuthenticationManager {
    private final UserDetailsService userDetailsService;

    public VerificationCodeAuthenticationManager(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            if ("3251".equals(usernamePasswordAuthenticationToken.getCredentials())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername((String) usernamePasswordAuthenticationToken.getPrincipal());
//                usernamePasswordAuthenticationToken.setAuthenticated(true);
                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                        userDetails, authentication.getCredentials(),
                        userDetails.getAuthorities());
                result.setDetails(authentication.getDetails());
                return result;
            } else {
                throw new InvalidGrantException("username and the verification_code is not matched. the sms code is ---> " + usernamePasswordAuthenticationToken.getCredentials());
            }
        }
        throw new InvalidGrantException("VerificationCodeAuthenticationManager setup is error, please call the programmer to check it");
    }
}
