package com.vluee.cloud.gateway.core.rbac;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 用于获取
 */
public interface AuthorizeResourceRepository {

    Mono<Void> save(Mono<AuthorizeResource> resource);

    Mono<Void> delete(Mono<String> resourceId);

    Mono<AuthorizeResource> load(Mono<String> resourceId);

    Flux<AuthorizeResource> findAll();
}
