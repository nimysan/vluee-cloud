package com.vluee.cloud.gateway.spring.security.authorization;

import com.vluee.cloud.gateway.core.rbac.ApiPermissionCheck;
import com.vluee.cloud.gateway.spring.security.IgnoreUrlsConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 读取UAMS模块写入REDIS的缓存数据
 */
@Slf4j
@Component
@AllArgsConstructor
public class ApiVersion2AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    private final ApiPermissionCheck apiPermissionCheck;

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

        //认证通过且角色匹配的用户可访问当前路径
        ServerWebExchangeDelegate build = ServerWebExchangeDelegate.builder().webExchange(authorizationContext.getExchange()).build();
        return authentication.map(this::debug).then(authentication)
                .filter(Authentication::isAuthenticated)
                .map(this::userKey)
                .flatMapIterable(t -> apiPermissionCheck.getRoleList(t))
                .any(t -> apiPermissionCheck.hasPermission(t, build.getApiKey()))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private String userKey(Authentication authentication) {
        return authentication.getName();
    }

    private Mono<Void> debug(Authentication authentication) {
        log.info("----- Authentication ----- {} with authorities: {} ", authentication.getPrincipal(), authentication.getAuthorities());
        return Mono.empty();
    }
}
