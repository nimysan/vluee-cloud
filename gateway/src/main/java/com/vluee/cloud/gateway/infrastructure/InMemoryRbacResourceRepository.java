package com.vluee.cloud.gateway.infrastructure;

import com.vluee.cloud.gateway.core.rbac.AuthorizeResource;
import com.vluee.cloud.gateway.core.rbac.AuthorizeResourceRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

public class InMemoryRbacResourceRepository implements AuthorizeResourceRepository {

    private final Map<String, AuthorizeResource> routes = synchronizedMap(
            new LinkedHashMap<String, AuthorizeResource>());


    @Override
    public Mono<Void> save(Mono<AuthorizeResource> resource) {
        //先删除,后插入 --- TODO 这样好像无法保证顺序 -- 间接的更新操作
        delete(resource.map(t -> t.getId())).onErrorResume(t -> Mono.empty()).block();
        return resource.flatMap(r -> {
            if (StringUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> resourceId) {
        return resourceId.flatMap(id -> {
            if (routes.containsKey(id)) {
                routes.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(
                    new NotFoundException("RbacResourceDefinition not found: " + resourceId)));
        });
    }

    @Override
    public Mono<AuthorizeResource> load(Mono<String> routeId) {
        return routeId.map(key -> routes.get(key)).switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<AuthorizeResource> findAll() {
        return Flux.fromIterable(routes.values());
    }
}
