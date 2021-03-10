package com.vluee.cloud.commons.common.rest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.StreamSupport;

import static com.vluee.cloud.commons.common.rest.AuthConstant.isMatched;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class AuthConstantTest {

    @Test
    public void testVerbMatch() {
        PathMatcher matcher = new AntPathMatcher();
        assertTrue(isMatched("* /hotels", HttpMethod.GET, "/HOTELS", matcher));
        assertTrue(isMatched("* /hotels", HttpMethod.POST, "/HOTELS", matcher));

        assertTrue(isMatched("GET /hotels", HttpMethod.GET, "/hotels", matcher));
        assertFalse(isMatched("POST /hotels", HttpMethod.GET, "/HOTELS", matcher));

        assertFalse(isMatched("invalid format", HttpMethod.GET, "/HOTELS", matcher));

    }

    @Test
    public void testRoleKey() {
        Mono<String> origin = Mono.just("123");
        origin.map(t -> "t-xxx").then(origin).map(t -> "XXX-ppp " + t).subscribe(System.out::println);
    }

    @Test
    public void testMatch() {
        Flux<String> mustRoleList = Flux.fromArray("r1,r2,r3,r4".split(","));
        Flux<String> fluxUserRoles = Flux.fromArray("r5,r6".split(","));
        //user roles list contain at least one role in mustRoleList
        Iterable<String> strings = mustRoleList.toIterable();
        fluxUserRoles.any(t -> StreamSupport.stream(strings.spliterator(), true).anyMatch(p -> t.equals(p))).subscribe(r -> log.info("--- {}", r));

//        String t = "";
//        fluxUserRoles.any(s -> s.equals("r1"));
    }
}