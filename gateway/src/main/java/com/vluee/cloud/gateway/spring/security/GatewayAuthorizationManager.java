package com.vluee.cloud.gateway.spring.security;

import com.vluee.cloud.gateway.core.events.AddResourceRoleEvent;
import com.vluee.cloud.gateway.core.events.DeleteResourceRoleEvent;
import com.vluee.cloud.gateway.core.events.RefreshAllEvent;
import com.vluee.cloud.gateway.core.rbac.AuthorizeResource;
import com.vluee.cloud.gateway.core.rbac.AuthorizeResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcherEntry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAnyRole;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

/**
 * 根据Uams设置的权限表，做授权操作
 */
@Slf4j
public class GatewayAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> staticMapping;

    private final AuthorizeResourceRepository authorizeRuleRepository;

    private final Map<String, ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> authorizeRuleMapping = new ConcurrentHashMap<String, ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>>();

    public GatewayAuthorizationManager(AuthorizeResourceRepository rbacResourceRepository, List<ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>>> mappings) {
        this.authorizeRuleRepository = rbacResourceRepository;
        if (mappings != null) {
            this.staticMapping = mappings;
        } else {
            this.staticMapping = Collections.emptyList();
        }
        this.updateAuthorizations();
    }

    @EventListener
    public void notifyAdded(AddResourceRoleEvent addResourceRoleEvent) {
        final Object source = addResourceRoleEvent.getSource();
        log.info("Notify add or update: {} ", source);
        this.updateAuthorization((String) source);
    }

    @EventListener
    public void notifyDeleted(DeleteResourceRoleEvent deleteResourceRoleEvent) {
        this.authorizeRuleMapping.remove(deleteResourceRoleEvent.getSource());
    }

    @EventListener
    public void handleRefresh(RefreshAllEvent refreshAllEvent) {
        this.updateAuthorizations();
    }

    //for testing
    public void handleRefresh() {
        this.updateAuthorizations();
    }

    /**
     * 更新指定的授权规则
     *
     * @param id 授权规则ID
     */
    public Mono<Void> updateAuthorization(final String id) {
        return authorizeRuleRepository.load(Mono.just(id)).map(this::convertToMatcher).then();
    }

    /**
     * 更新所有授权规则
     */
    public synchronized Mono<Void> updateAuthorizations() {
        authorizeRuleMapping.clear();
        authorizeRuleRepository.findAll().map(this::convertToMatcher).subscribe();
        return Mono.empty();
    }


    public Mono<Void> deletePermission(String ruleId) {
        authorizeRuleMapping.remove(ruleId);
        return Mono.empty();
    }

    public Mono<Void> addOrUpdatePermission(String ruleId) {
        return authorizeRuleRepository.load(Mono.just(ruleId)).map(this::convertToMatcher).then();
    }


    /**
     * @param authorizeResource
     * @return
     */
    protected ServerWebExchangeMatcherEntry<ReactiveAuthorizationManager<AuthorizationContext>> convertToMatcher(AuthorizeResource authorizeResource) {
        try {
            ServerWebExchangeMatcher matcher;
            if (StringUtils.isNotBlank(authorizeResource.getVerb())) {
                matcher = pathMatchers(HttpMethod.resolve(authorizeResource.getVerb()), authorizeResource.getUrl());
            } else {
                matcher = pathMatchers(authorizeResource.getUrl());
            }
            ServerWebExchangeMatcherEntry serverWebExchangeMatcherEntry;
            if (authorizeResource.isPermitAll()) {
                serverWebExchangeMatcherEntry = new ServerWebExchangeMatcherEntry(matcher, (ReactiveAuthorizationManager) (authentication, object) -> Mono.just(new AuthorizationDecision(true)));
            } else {
                serverWebExchangeMatcherEntry = new ServerWebExchangeMatcherEntry(matcher, hasAnyRole(authorizeResource.getAuthorities().stream().toArray(String[]::new)));
            }
            authorizeRuleMapping.put(authorizeResource.getId(), serverWebExchangeMatcherEntry);
            return serverWebExchangeMatcherEntry;
        } catch (Exception e) {
            log.error("Failed to convert {}", authorizeResource, e);
        }
        return null;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication.map(this::debug).then(Flux.fromIterable(authorizeRuleMapping.values())
                .concatWith(Flux.fromIterable(staticMapping))
                .concatMap(mapping -> mapping.getMatcher().matches(authorizationContext.getExchange())
                        .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
                        .map(ServerWebExchangeMatcher.MatchResult::getVariables)
                        .flatMap(variables -> mapping.getEntry().check(authentication, new AuthorizationContext(authorizationContext.getExchange(), variables)))
                )
                .next()
                .defaultIfEmpty(new AuthorizationDecision(false)));
    }

    private Mono<Void> debug(Authentication authentication) {
        log.info("----- Authentication ----- {} with authorities: {} ", authentication.getPrincipal(), authentication.getAuthorities());
        authorizeRuleMapping.forEach((k, v) -> {
            log.info("--- {} ---  {} ", k, v.getMatcher(), v.getEntry());
        });
        return Mono.empty();
    }

    public Flux<String> showRules() {
        return Flux.fromIterable(authorizeRuleMapping.values()).map(t -> t.getMatcher().toString() + " need: " + t.getEntry().toString());
    }
}
