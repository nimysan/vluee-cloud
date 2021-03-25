package com.vluee.cloud.auth.spring;

import com.vluee.cloud.auth.spring.security.filter.VerificationCodeTokenGranter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 配置短信验证码/微信认证等认证方式以获取token
 */
@Configuration
public class IntegrationTokenGranterConfig {
    @Bean
    public VerificationCodeTokenGranter verificationCodeTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        return new VerificationCodeTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory);
    }
}
