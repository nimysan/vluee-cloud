package com.vluee.cloud.auth.domain.client;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class BCryptPasswordEncoderTest {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void test() {
        assertFalse(encoder.matches("123", "123"));
        assertTrue(encoder.matches("123", encoder.encode("123")));
        log.info("--pass {} , encoded to {}", "124654", encoder.encode("124654"));
    }
}
