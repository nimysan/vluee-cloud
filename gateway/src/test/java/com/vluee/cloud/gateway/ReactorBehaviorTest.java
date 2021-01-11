package com.vluee.cloud.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 业务无关， 行为理解测试
 */
@Slf4j
public class ReactorBehaviorTest {

    @Test
    public void testMonoTransfer() {
        String xa = "23344";
        Mono<String> test = Mono.just("23344");

        test.flatMap(this::newMono).subscribe(t -> {
            assertEquals("new mono --- " + xa, t);
        });
    }

    public Mono<String> newMono(String t) {
        return Mono.just("new mono --- " + t);
    }

    /**
     * 测试因为异常导致流程中断
     */
    @Test
    public void testMonoError() {
        try {
            Mono<Object> objectMono = Mono.justOrEmpty(new Object());
            test().subscribe(d -> log.info("{}", d));
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Test
    public void testSwitchNewMonoThenException() {
        switchNewMonoThenException().subscribe(d -> log.info("{}", d));
    }

    private Mono<Void> switchNewMonoThenException() {
        return Mono.empty()
                .switchIfEmpty(Mono.just("hello"))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AccessDeniedException("Access Denied"))))
                .flatMap(d -> Mono.empty());
    }

    private Mono<Void> test() {
        return Mono.empty()
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AccessDeniedException("Access Denied"))))
                .flatMap(d -> Mono.empty());
    }
}
