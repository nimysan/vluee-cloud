package com.vluee.cloud.gateway.infrastructure;

import com.vluee.cloud.gateway.core.rbac.AuthorizeResource;
import com.vluee.cloud.gateway.core.rbac.AuthorizeResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import static com.vluee.cloud.commons.common.AiStoreConstants.AUTH_ROLE_AUTHORITY_MAP;

@Component
@Slf4j
public class RedisAuthorizeResourceRepository implements AuthorizeResourceRepository {

    public RedisAuthorizeResourceRepository(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @PostConstruct
    public void init() {
        log.info("RedisAuthorizeRuleRepository initialized ");
    }

    @Override
    public Mono<Void> save(Mono<AuthorizeResource> resource) {
        return resource.flatMap(mono -> {
            return reactiveRedisTemplate.opsForHash().put(AUTH_ROLE_AUTHORITY_MAP, mono.getId(), mono.toJsonString());
        }).then();
    }

    @Override
    public Mono<Void> delete(Mono<String> resourceId) {
        return resourceId.flatMap(t -> {
            return reactiveRedisTemplate.opsForHash().remove(AUTH_ROLE_AUTHORITY_MAP, t);
        }).then();
    }

    @Override
    public Mono<AuthorizeResource> load(Mono<String> resourceId) {
        return resourceId.flatMap(t -> reactiveRedisTemplate.opsForHash().get(AUTH_ROLE_AUTHORITY_MAP, t)).map(this::convert);
    }

    @Override
    public Flux<AuthorizeResource> findAll() {
        return reactiveRedisTemplate.opsForHash().values(AUTH_ROLE_AUTHORITY_MAP).map(this::convert);
    }

    private AuthorizeResource convert(Object objString) {
        if (objString == null) {
            return null;
        }
        return AuthorizeResource.buildFromJson((String) objString);
    }
}