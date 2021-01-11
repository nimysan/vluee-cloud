package com.vluee.cloud.gateway.infrastructure;

import com.vluee.cloud.gateway.core.rbac.AuthorizeResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@SpringBootTest
@Slf4j
class RedisAuthorizeRuleRepositoryTest {


    @Autowired
    private RedisAuthorizeResourceRepository redisAuthorizeRuleRepository;

    @Test
    public void testGetRule() {
        redisAuthorizeRuleRepository.save(Mono.just(AuthorizeResource.builder().id("1000111").verb("*").url("/doc.html").permitAll(true).authorities(Arrays.asList("a1", "a2")).build())).block();
        redisAuthorizeRuleRepository.load(Mono.just("123"))
                .subscribe(t -> log.info("---- xxx {}", t.getAuthorities()));
    }

}