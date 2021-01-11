package com.vluee.cloud.gateway.context;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @TestConfiguration 是基于主配置的<strong>附加</strong>配置
 */
@TestConfiguration
public class MockConfiguration {
    @Bean
    public Object userDetailService() {
        return new MockFoo("hello");
    }
}