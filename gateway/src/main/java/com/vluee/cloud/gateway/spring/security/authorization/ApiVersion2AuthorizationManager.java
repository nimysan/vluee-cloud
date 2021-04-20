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
import java.util.function.Consumer;

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
        return authentication
                .filter(Authentication::isAuthenticated)
                .map(t -> authorizationObject.initializeWithUsername(this.username(t)))//.map(this::authorizationObject)
                .thenMany(apiSetups).filter(t -> AuthConstant.isMatched(t, realRequest.getMethod(), realRequest.getUrl(), pathMatcher))
                .next()
                .flatMapMany(this::apiRoles)
                .any(authorizationObject::hasRole)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }


    private Flux<String> apiRoles(String apiKey) {
        Mono<String> mono = redisTemplate.opsForHash().get(AuthConstant.API_ROLES_MAP_KEY, apiKey);
        return mono.map(s -> {
            if (StringUtils.isNotEmpty(s)) {
                return Arrays.asList(s.split(","));
            }
            return Collections.emptyList();
        }).flatMapMany(Flux::fromIterable).map(Object::toString);
    }

    private String username(Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Jwt principal = (Jwt) token.getPrincipal();
        return principal.getClaim("user_name").toString();
    }

    class AuthorizationObject {

        private String username;
        private List<String> roles;

        public boolean hasRole(String roleName) {
            if (this.roles != null) {
                return this.roles.contains(roleName);
            }
            return false;
        }

        public AuthorizationObject initializeWithUsername(String username) {
            this.username = username;
            redisTemplate.opsForHash().get(AuthConstant.USER_ROLES_MAP_KEY, username).subscribe(new Consumer() {
                @Override
                public void accept(Object o) {
                    String s = (String) o;
                    if (StringUtils.isNotEmpty(s)) {
                        roles = Arrays.asList(s.split(","));
                    }
                }
            });
            return this;
        }
    }
}
