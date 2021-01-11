package com.vluee.cloud.uams.constraints;


import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.uams.core.brand.service.BrandQueryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintDeclarationException;
import javax.validation.constraints.NotBlank;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 1. 需要验证的东西，必须加上 @Validated 声明
 * <p>
 * 2. 规则建议定义在接口上， 实现类不能覆盖在接口定义的规则， 否则会产生  javax.validation.ConstraintDeclarationException
 *
 * @see ConstraintDeclarationException
 */

@SpringBootTest
@Slf4j
public class ConstraintDeclarationExceptionTest {


    @MockBean
    private AuditContext auditContext;

    @MockBean
    private BrandQueryService brandQueryService;

    @Autowired
    private TestService testService;

    @Test
    public void testDiffAtInterfaceAndClass() {
        assertThrows(javax.validation.ConstraintDeclarationException.class, () -> testService.interfaceLevelDeclaration(null), "esttt");
    }

    @TestConfiguration
    @Slf4j
    public static class AppConfig {
        @Bean
        public TestService validationPostProcessor() {
            return new TestService();
        }
    }

    @Validated
    public static interface ITestService {
        public String interfaceLevelDeclaration(@NotBlank(message = "msg at interface") String prefix);
    }

    @Slf4j
    public static class TestService implements ITestService {
        @Override
        public String interfaceLevelDeclaration(@NotBlank(message = "msg at implements") String prefix) {
            return prefix + " - jey";
        }
    }


}
