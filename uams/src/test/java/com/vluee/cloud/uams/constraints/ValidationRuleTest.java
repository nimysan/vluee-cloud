package com.vluee.cloud.uams.constraints;


import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.uams.core.brand.service.BrandQueryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintDeclarationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class ValidationRuleTest {


    @MockBean
    private AuditContext auditContext;

    @MockBean
    private BrandQueryService brandQueryService;

    @Autowired
    private TestService testService;

    @Test
    public void testRuleIsActive() {
        String prefix = "hello";
        assertThrows(javax.validation.ConstraintViolationException.class, () -> testService.testDisplay(new Pojo(null, null), "esttt"));
        log.info("Test finished");
    }

    @Test
    public void testNotWorkAtInterface() {
        String prefix = "hello";
        assertThrows(javax.validation.ConstraintViolationException.class, () -> testService.interfaceLevelDeclaration(new Pojo(null, null), "esttt"));
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pojo {

        @NotNull(message = "姓名不能为空")
        private String name;

        private String desc;

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

        public void testDisplay(@Valid Pojo pojo, @NotNull(message = "prefix不能为空") String prefix);

        public void testDiffValidationMessage(@NotBlank(message = "interface-checking") String prefix);

        /**
         * 接口级别的验证声明不生效
         *
         * @param pojo
         * @param prefix
         * @return
         */
        public String interfaceLevelDeclaration(@Valid Pojo pojo, @NotBlank String prefix);
    }

    @Slf4j
    public static class TestService implements ITestService {

        public void testDisplay(Pojo pojo, String prefix) {
            log.info("-- PREFIX {}  --- ,  -- -{}---", prefix, ToStringBuilder.reflectionToString(pojo));
        }

        /**
         * 实现类不可以Override验证规则，
         * 错误信息:
         * <pre>
         *     HV000151: A method overriding another method must not redefine the parameter constraint configuration, but method TestService#testDiffValidationMessage(String) redefines the configuration of ITestService#testDiffValidationMessage(String).
         *
         * </pre>
         *
         * @param prefix
         */
        public void testDiffValidationMessage(String prefix) {
            log.info("-- PREFIX {}  --- ,  -- -{}---", prefix);
        }


        public String interfaceLevelDeclaration(Pojo pojo, String prefix) {
            return "XXX - " + prefix + " -- " + ToStringBuilder.reflectionToString(pojo);
        }

    }


}
