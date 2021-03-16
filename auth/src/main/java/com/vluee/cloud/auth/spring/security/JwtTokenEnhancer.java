package com.vluee.cloud.auth.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        log.info("Authentication is {}", authentication);
//        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
//        java.util.Map<String, Object> info = new HashMap<>();
//        //把用户ID设置到JWT中
//        info.put("id", securityUser.getId());
//        info.put("client_id", securityUser.getClientId());
//        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}