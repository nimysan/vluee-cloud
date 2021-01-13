package com.vluee.cloud.gateway.spring.security;

import com.vluee.cloud.commons.common.rest.AuthConstant;
import com.vluee.cloud.gateway.core.filter.IgnoreUrlsRemoveJwtFilter;
import com.vluee.cloud.gateway.spring.security.authorization.RestResourceAuthorizationManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Security组件逻辑请参见： WebFluxSecurityConfiguration
 */
@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
@Slf4j
public class ResourceServerConfig {

    private final RestResourceAuthorizationManager authorizationManager;

    private final AccessDeniedHandler _403Handler;
    private final UnauthenticatedHandler _401Handler;

    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // JWT解析和设定
        http
                .oauth2ResourceServer()
                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

        //对白名单路径，直接移除JWT请求头
        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        //未认证处理器，未授权处理器， 以及授权规则解析
        http
                .authorizeExchange()
                .anyExchange()
                .access(authorizationManager)
                .and().exceptionHandling()
                .accessDeniedHandler(_403Handler)//处理未授权 403 FORBIDDEN
                .authenticationEntryPoint(_401Handler)//处理未认证 401 UNAUTHENTICATED
                //其他安全设定
                .and().csrf().disable();
        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}

