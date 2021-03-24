package com.vluee.cloud.auth.spring.security.filter;

import com.vluee.cloud.auth.core.sms.domain.SmsCode;
import com.vluee.cloud.auth.core.sms.domain.SmsCodeRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter implements ApplicationContextAware {

    private static final String AUTH_TYPE_PARM_NAME = "auth_type";

//    private Collection<IntegrationAuthenticator> authenticators;

    private ApplicationContext applicationContext;

    @Autowired
    private SmsCodeRepository smsCodeRepository;

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/oauth/xxx**", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String userClientId = request.getParameter("user-client-id");
        Optional<SmsCode> smsCode = smsCodeRepository.loadByUserClientId(userClientId);
        if (smsCode.isPresent()) {
            String codeValue = request.getParameter("smsCode");
            if (smsCode.get().getCodeValue() == Integer.parseInt(codeValue)) {
                //验证成功  ----
                //验证失败
            }
        }
        return new MobileSmsCodeAuthentication();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public class MobileSmsCodeAuthentication implements Authentication {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return null;
        }
    }
}
