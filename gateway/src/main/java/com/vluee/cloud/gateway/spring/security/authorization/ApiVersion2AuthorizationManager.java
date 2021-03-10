package com.vluee.cloud.gateway.spring.security.authorization;

import com.vluee.cloud.commons.common.rest.AuthConstant;
import com.vluee.cloud.commons.common.string.StringUtils;
import com.vluee.cloud.gateway.spring.security.IgnoreUrlsConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * 读取UAMS模块写入REDIS的缓存数据
 */
@Slf4j
@Component
@AllArgsConstructor
public class ApiVersion2AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    private final ReactiveRedisTemplate redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();

        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }
        //对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        Flux<String> apiSetups = redisTemplate.opsForHash().keys(AuthConstant.API_ROLES_MAP_KEY);

        //认证通过且角色匹配的用户可访问当前路径
        ServerWebExchangeDelegate realRequest = ServerWebExchangeDelegate.builder().webExchange(authorizationContext.getExchange()).build();
        AuthorizationObject authorizationObject = new AuthorizationObject();
        return authentication.map(this::debug).then(authentication)
                .filter(Authentication::isAuthenticated)
                .map(t -> authorizationObject.setUsername(this.username(t)))//.map(this::authorizationObject)
                .thenMany(apiSetups).filter(t -> AuthConstant.isMatched(t, realRequest.getMethod(), realRequest.getUrl(), pathMatcher))
                .next()
                .flatMapMany(this::apiRoles)
                .any(t -> StreamSupport.stream(this.getUserRoles(authorizationObject).spliterator(), false).anyMatch(p -> t.equals(p)))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }


    private Iterable<String> getUserRoles(AuthorizationObject authorizationObject) {
        log.info("---- log user uane --- ", authorizationObject.username);
        String s = (String) redisTemplate.opsForHash().get(AuthConstant.USER_ROLES_MAP_KEY, authorizationObject.username).block();
        if (StringUtils.isNotEmpty(s)) {
            return Arrays.asList(s.split(","));
        }
        return Collections.emptyList();
    }


    private Flux<String> apiRoles(String apiKey) {
        Mono<String> mono = redisTemplate.opsForHash().get(AuthConstant.API_ROLES_MAP_KEY, apiKey);
        return (Flux<String>) mono.map(s -> {
            if (StringUtils.isNotEmpty(s)) {
                return Arrays.asList(s.split(","));
            }
            return Collections.emptyList();
        }).flatMapMany(Flux::fromIterable);
    }

    private String username(Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Jwt principal = (Jwt) token.getPrincipal();
        return principal.getClaim("user_name").toString();
    }

    private Mono<Void> debug(Authentication authentication) {
        log.info("----- Authentication ----- {} with authorities: {} ", authentication.getPrincipal(), authentication.getAuthorities());
        return Mono.empty();
    }

    class AuthorizationObject {

        private String username;

        public AuthorizationObject setUsername(String username) {
            this.username = username;
            return this;
        }


        private void getRoles() {
            redisTemplate.opsForHash().get(AuthConstant.USER_ROLES_MAP_KEY, username);
        }
    }
}
