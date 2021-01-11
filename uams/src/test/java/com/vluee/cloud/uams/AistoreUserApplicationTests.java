package com.vluee.cloud.uams;

import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.uams.core.brand.service.BrandQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class AistoreUserApplicationTests {

    @MockBean
    private BrandQueryService saasBrandQueryService;

    @Test
    void contextLoads() {
    }

    @TestConfiguration
    public static class ForMe {

        @Bean
        public AuditContext auditContext() {
            return new AuditContext() {
                @Override
                public String getOpId() {
                    return "hey";
                }
            };
        }
    }

}
