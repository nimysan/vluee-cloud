package com.vluee.cloud.gateway.spring.security.authorization;

import com.vluee.cloud.gateway.core.rbac.RestGrantRule;
import com.vluee.cloud.gateway.core.rbac.RestGrantRuleService;
import com.vluee.cloud.gateway.spring.security.IgnoreUrlsConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.net.URI;
import java.util.List;

/**
 * 根据Uams设置的权限表，做授权操作, 权限表得内容都在: AuthConstant.RESOURCE_ROLES_MAP_KEY
 * <p>
 *
 * @see ServerBearerTokenAuthenticationConverter
 */
@Slf4j
@Component
@AllArgsConstructor
public class RestResourceAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    private final RestGrantRuleService restGrantRuleService;

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

        //如果匹配， 则成功
        RestGrantRule effectiveRule = restGrantRuleService.findRule(ServerWebExchangeDelegate.builder().webExchange(authorizationContext.getExchange()).build());
        //如果没有任何匹配规则， 直接拒绝请求
        if (effectiveRule == null) {
            log.debug("no rule is matched with given request path, denied access");
            return Mono.just(new AuthorizationDecision(false));
        } else {
            log.debug("Effective rule is {}", effectiveRule);
        }

        //认证通过且角色匹配的用户可访问当前路径
        return authentication.map(this::debug).then(authentication)
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(effectiveRule.getRoles()::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private Mono<Void> debug(Authentication authentication) {
        log.info("----- Authentication ----- {} with authorities: {} ", authentication.getPrincipal(), authentication.getAuthorities());
        return Mono.empty();
    }
}
