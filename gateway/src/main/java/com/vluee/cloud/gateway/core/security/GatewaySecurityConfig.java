package com.vluee.cloud.gateway.core.security;

import com.vluee.cloud.gateway.core.rbac.AuthorizeResourceRepository;
import com.vluee.cloud.gateway.interfaces.actuate.RuleActuateControllerEndpoint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.util.FieldUtils;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
import org.springframework.security.web.server.authorization.DelegatingReactiveAuthorizationManager;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcherEntry;

import java.util.Collections;
import java.util.List;

/**
 * Security组件逻辑请参见： WebFluxSecurityConfiguration
 */
@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
@Slf4j
public class GatewaySecurityConfig {

    @Autowired
    private final AuthorizeResourceRepository rbacResourceRepository;

    private final GatewayAccessDeniedHandler _403Handler;

    private final Converter<Jwt, AbstractAuthenticationToken> tokenConverter;

    @Bean
    public ServerAuthenticationEntryPoint _401Handler() {
        return new GatewayServerAuthenticationEntryPoint();
    }

    // @formatter:off
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .oauth2ResourceServer()
                .jwt().jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(tokenConverter));

        http
                .authorizeExchange().anyExchange().permitAll()

//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                .pathMatchers("/actuator/**").permitAll() // actuator统一管理
//                .pathMatchers("/doc.html", "/**/v2/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/aistore-tenant/**").permitAll()
//                .pathMatchers("/webjars/**", "/favicon.ico", "/*.js", "/*.html", "/*.ico", "/*.png").permitAll()
//                .pathMatchers("/user/me").permitAll()
//                .anyExchange()
//                .access(additionalAuthorizationManager(Collections.emptyList()))

                .and().exceptionHandling()
                .accessDeniedHandler(_403Handler)//处理未授权 403 FORBIDDEN
                .authenticationEntryPoint(_401Handler())//处理未认证 401 UNAUTHENTICATED
                .and().csrf().disable();

        /**
         * 1. 正常配置
         * 2. 将配置好的AuthorizationWebFilter丢对象里面的ReactiveAuthorizationManager替换为定制的。
         * 3. 定制的ReactiveAuthorizationManager集成了之前配置的所有规则。 然后合并动态配置的规则确保规则合并和生效
         * 4. 因为Spring security默认框架的原因，很多东西不能改动。 采用反射机制来实现.
         * 5. AuthorizationWebFilter不能出现多个. 否则无法正确判断当authentication缺失的时候的行为, spring security框架默认会将无authentication的请求全部转给下一个filter.
         *    从而间接导致配置在前面的AuthorizationWebFilter规则失效
         */
        SecurityWebFilterChain securityWebFilterChain = http.build();

        if (false) {
            //以上为替换AuthorizationWebFilter中ReactiveAuthorizationManager的完整逻辑
            AuthorizationWebFilter authorizationWebFilter = (AuthorizationWebFilter) securityWebFilterChain.getWebFilters().filter(w -> AuthorizationWebFilter.class.equals(w.getClass())).blockFirst();
            Object accessDecisionManager = FieldUtils.getProtectedFieldValue("accessDecisionManager", authorizationWebFilter);
            List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> mappings;
            if (accessDecisionManager instanceof DelegatingReactiveAuthorizationManager) {
                DelegatingReactiveAuthorizationManager authorizationManager = (DelegatingReactiveAuthorizationManager) accessDecisionManager;
                mappings = (List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>>) FieldUtils.getProtectedFieldValue("mappings", accessDecisionManager);
            } else {
                mappings = Collections.emptyList();
            }
            FieldUtils.setProtectedFieldValue("accessDecisionManager", authorizationWebFilter, additionalAuthorizationManager(mappings));
            //以上为替换AuthorizationWebFilter中ReactiveAuthorizationManager的完整逻辑
        }

        return securityWebFilterChain;
    }
    // @formatter:on

    @Bean
    public ReactiveAuthorizationManager additionalAuthorizationManager(List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> staticMappings) {
        return new GatewayAuthorizationManager(rbacResourceRepository, staticMappings);
    }


    @Bean
    @ConditionalOnClass(Health.class)
    public Object ruleActuator(GatewayAuthorizationManager authorizationManager) {
        return new RuleActuateControllerEndpoint(authorizationManager);
    }
}

