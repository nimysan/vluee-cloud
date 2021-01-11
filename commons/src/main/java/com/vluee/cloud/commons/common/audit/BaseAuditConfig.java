package com.vluee.cloud.commons.common.audit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基础的audit context
 */
@Configuration
public class BaseAuditConfig {

    @Bean
    @ConditionalOnMissingBean
    public AuditContext auditContext() {
        return new AuditContext() {
            @Override
            public String getOpId() {
                return "system-default";
            }
        };
    }

}



