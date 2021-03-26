package com.vluee.cloud.auth.spring.security.tokengranter;

import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 参考
 *
 * @see org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter
 * @see AuthorizationServerEndpointsConfigurer
 */
public class VerificationCodeTokenGranter extends AbstractTokenGranter {

    private final AuthenticationManager authenticationManager;

    public VerificationCodeTokenGranter(AuthenticationManager authenticationManager, ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter) {
        //将这些重要的组件从 ResourceOwnerPasswordTokenGranter copy到新的granter中
        super(
                (AuthorizationServerTokenServices) BeanUtil.getFieldValue(resourceOwnerPasswordTokenGranter, "tokenServices"),
                (ClientDetailsService) BeanUtil.getFieldValue(resourceOwnerPasswordTokenGranter, "clientDetailsService"),
                (OAuth2RequestFactory) BeanUtil.getFieldValue(resourceOwnerPasswordTokenGranter, "requestFactory"),
                ExtGrantType.verification_code.name());
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        //here retrieve the parameters
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

        if (StringUtils.isBlank(parameters.get("userClientId"))) {
            throw new InvalidGrantException("参数中没有userClientId");
        }

        //定制部分
        Authentication userAuth = retrieveAuthentication(tokenRequest);
        //定制部分

        String username = (String) userAuth.getPrincipal();

        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

    protected Authentication retrieveAuthentication(TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String codeKey = "verification_code";
        String username = parameters.get("username");

        String password = parameters.get(codeKey);
        // Protect from downstream leaks of password
        parameters.remove(codeKey);
        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        return userAuth;
    }
}
