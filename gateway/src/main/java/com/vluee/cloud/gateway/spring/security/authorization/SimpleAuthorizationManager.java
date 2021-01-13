package com.vluee.cloud.gateway.spring.security.authorization;

import cn.hutool.core.convert.Convert;
import com.vluee.cloud.gateway.core.filter.AuthConstant;
import com.vluee.cloud.gateway.spring.security.IgnoreUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 根据Uams设置的权限表，做授权操作, 权限表得内容都在: AuthConstant.RESOURCE_ROLES_MAP_KEY
 * <p>
 *
 * @see ServerBearerTokenAuthenticationConverter
 */
@Slf4j
@Component
public class SimpleAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private RedisTemplate redisTemplate;

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

        //管理端路径需校验权限
        Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
        List<String> authorities = new ArrayList<>();
        while (iterator.hasNext()) {
            String pattern = (String) iterator.next();
            if (pathMatcher.match(pattern, uri.getPath())) {
                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
            }
        }
        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return authentication.map(this::debug).then(authentication)
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private Mono<Void> debug(Authentication authentication) {
        log.info("----- Authentication ----- {} with authorities: {} ", authentication.getPrincipal(), authentication.getAuthorities());
        return Mono.empty();
    }

    @PostConstruct
    public void setupResourcesForTesting() {
        redisTemplate.opsForHash().put(AuthConstant.RESOURCE_ROLES_MAP_KEY, "/saas-users/users/**", "admin");
        redisTemplate.opsForHash().put(AuthConstant.RESOURCE_ROLES_MAP_KEY, "/saas-users/users/roles", "users-admin");
        redisTemplate.opsForHash().put(AuthConstant.RESOURCE_ROLES_MAP_KEY, "/saas-users/users/read", "users-read");
        log.info("测试规则输入成功");
    }

}
