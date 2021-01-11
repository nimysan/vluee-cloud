package com.vluee.cloud.gateway.spring.security;

import cn.hutool.core.util.ArrayUtil;
import com.vluee.cloud.gateway.core.filter.AuthConstant;
import com.vluee.cloud.gateway.core.filter.IgnoreUrlsRemoveJwtFilter;
import com.vluee.cloud.gateway.core.rbac.AuthorizeResourceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcherEntry;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Security组件逻辑请参见： WebFluxSecurityConfiguration
 */
@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
@Slf4j
public class ResourceServerConfig {

    private final AuthorizeResourceRepository rbacResourceRepository;

    private final AccessDeniedHandler _403Handler;

    private final Converter<Jwt, AbstractAuthenticationToken> tokenConverter;

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    private final SimpleAuthorizationManager authorizationManager;

    @Bean
    public ServerAuthenticationEntryPoint _401Handler() {
        return new UnauthenticatedHandler();
    }

    // @formatter:off
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // JWT解析和设定
        http
                .oauth2ResourceServer()
                //.jwt().jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(tokenConverter));
                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

        //对白名单路径，直接移除JWT请求头
        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        // 规则设定, 白名单等
        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class)).permitAll()//白名单配置
                .pathMatchers("/actuator/**").permitAll() // actuator统一管理
                .anyExchange()
                .access(authorizationManager)
                .and().exceptionHandling()
                .accessDeniedHandler(_403Handler)//处理未授权 403 FORBIDDEN
                .authenticationEntryPoint(_401Handler())//处理未认证 401 UNAUTHENTICATED
                .and().csrf().disable();
        return http.build();
    }
    // @formatter:on

    @Bean
    public ReactiveAuthorizationManager additionalAuthorizationManager(List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> staticMappings) {
        return new GatewayAuthorizationManager(rbacResourceRepository, staticMappings);
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

