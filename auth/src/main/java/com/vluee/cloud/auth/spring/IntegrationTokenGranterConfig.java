package com.vluee.cloud.auth.spring;

import cn.hutool.core.util.ReflectUtil;
import com.vluee.cloud.auth.spring.security.filter.VerificationCodeTokenGranter;
import com.vluee.cloud.auth.spring.security.filter.intergrationauth.VerificationCodeAuthenticationManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配置短信验证码/微信认证等认证方式以获取token
 */
@Component
@AllArgsConstructor
@Slf4j
public class IntegrationTokenGranterConfig implements ApplicationRunner {

    private final TokenEndpoint tokenEndpoint;
    private final AuthorizationServerTokenServices tokenServices;
    private final ClientDetailsService clientDetailsService;
    private final UserDetailsService userDetailsService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TokenGranter tokenGranter = (TokenGranter) ReflectUtil.getFieldValue(tokenEndpoint, "tokenGranter");
        try {
            OAuth2AccessToken verify_code = tokenGranter.grant("verify_code", null);
            log.info("---- token granter ----", tokenGranter);
            Object delegate = ReflectUtil.getFieldValue(tokenGranter, "delegate");
            if (delegate instanceof CompositeTokenGranter) {
                CompositeTokenGranter granter = (CompositeTokenGranter) delegate;
                List<TokenGranter> existGranters = (List<TokenGranter>) ReflectUtil.getFieldValue(granter, "tokenGranters");
                ResourceOwnerPasswordTokenGranter copyFromGranter = (ResourceOwnerPasswordTokenGranter) existGranters.stream().filter(t -> t instanceof ResourceOwnerPasswordTokenGranter).findFirst().get();
                granter.addTokenGranter(new VerificationCodeTokenGranter(verificationCodeAuthenticationManager(userDetailsService), copyFromGranter));
                log.info("Add new token granter down {}", granter);
            }
        } catch (Exception exception) {
            log.error("exxxxx ", exception);
        }
    }

    private VerificationCodeAuthenticationManager verificationCodeAuthenticationManager(UserDetailsService userDetailsService) {
        return new VerificationCodeAuthenticationManager(userDetailsService);
    }
}
