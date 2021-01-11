package com.vluee.cloud.commons.common.data.id;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdConfig {
    @Bean
    public LongIdGenerator idGenerator() {
        return new SnowflakeIdGenerator();
    }
}
