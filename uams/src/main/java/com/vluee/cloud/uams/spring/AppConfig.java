package com.vluee.cloud.uams.spring;

import com.vluee.cloud.commons.common.audit.AuditContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AuditContext auditContext() {
        return new AuditContext() {
            @Override
            public String getOpId() {
                return null;
            }
        };
    }
}
