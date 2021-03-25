package com.vluee.cloud.auth.spring.security.filter;

import com.vluee.cloud.auth.core.sms.domain.SmsCodeRepository;
import com.vluee.cloud.auth.spring.security.filter.intergrationauth.FooAuthentication;
import com.vluee.cloud.auth.spring.security.filter.intergrationauth.IntegrationAuthenticator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 第三方集成登录验证工具
 */
public class IntegrationAuthenticationProcessingFilter extends GenericFilterBean implements ApplicationContextAware {

    private static final String AUTH_TYPE_PARM_NAME = "auth_type";

    private static final String OAUTH_TOKEN_URL = "/oauth/token";

    private Collection<IntegrationAuthenticator> authenticators;

    private RequestMatcher requestMatcher;

    private ApplicationContext applicationContext;

    @Autowired
    private SmsCodeRepository smsCodeRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public IntegrationAuthenticationProcessingFilter() {
        this.requestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "GET"),
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST")
        );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (requestMatcher.matches((HttpServletRequest) request)) {
            FooAuthentication fooAuthentication = createAuthentication();
            this.prepare(fooAuthentication);
            chain.doFilter(request, response);
            this.complete(fooAuthentication);
        } else {
            chain.doFilter(request, response);
        }
    }

    private FooAuthentication createAuthentication() {
        return new FooAuthentication("1234");
    }

    private void prepare(FooAuthentication authentication) {
        logger.info("---- Integration auth at PREPARE Phase");
        //延迟加载认证器
        if (this.authenticators == null) {
            synchronized (this) {
                Map<String, IntegrationAuthenticator> integrationAuthenticatorMap = applicationContext.getBeansOfType(IntegrationAuthenticator.class);
                if (integrationAuthenticatorMap != null) {
                    this.authenticators = integrationAuthenticatorMap.values();
                }
            }
        }

        if (this.authenticators == null) {
            this.authenticators = new ArrayList<>();
        }

        for (IntegrationAuthenticator authenticator : authenticators) {
            if (authenticator.support(authentication)) {
                authenticator.prepare(authentication);
            }
        }
    }

    private void complete(FooAuthentication authentication) {
        logger.info("---- Integration auth at COMPLETE Phase");
        for (IntegrationAuthenticator authenticator : authenticators) {
            if (authenticator.support(authentication)) {
                authenticator.complete(authentication);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
