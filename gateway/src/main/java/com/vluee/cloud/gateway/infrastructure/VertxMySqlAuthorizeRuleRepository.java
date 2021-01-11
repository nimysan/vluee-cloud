package com.vluee.cloud.gateway.infrastructure;

import com.vluee.cloud.gateway.core.rbac.AuthorizeResource;
import com.vluee.cloud.gateway.core.rbac.AuthorizeResourceRepository;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Slf4j
public class VertxMySqlAuthorizeRuleRepository implements AuthorizeResourceRepository {

    public VertxMySqlAuthorizeRuleRepository(MySQLPool sqlPool) {
        this.mySQLPool = sqlPool;
    }

    private final MySQLPool mySQLPool;

    @PostConstruct
    public void init() {
        log.info("VertxMySqlAuthorizeRuleRepository initialized ");
    }

    @Override
    public Mono<Void> save(Mono<AuthorizeResource> resource) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> resourceId) {
        return null;
    }

    @Override
    public Mono<AuthorizeResource> load(Mono<String> routeId) {
        return null;
    }

    @Override
    public Flux<AuthorizeResource> findAll() {
        mySQLPool.query("SELECT * FROM users WHERE id='julien'")
                .execute(ar -> {
                    if (ar.succeeded()) {
                        RowSet<Row> result = ar.result();
                        System.out.println("Got " + result.size() + " rows ");
                    } else {
                        System.out.println("Failure: " + ar.cause().getMessage());
                    }
                });
        return Flux.empty();

    }
}