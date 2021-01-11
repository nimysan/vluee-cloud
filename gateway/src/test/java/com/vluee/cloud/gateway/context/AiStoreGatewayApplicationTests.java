package com.vluee.cloud.gateway.context;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 用于验证测试环境和测试组件是否正确装配
 * <p>
 * 详细文档请参考： https://docs.spring.io/spring-boot/docs/2.2.8.RELEASE/reference/htmlsingle/#boot-features-testing
 */
@ActiveProfiles("try")
@SpringBootTest(args = "--app.test=one")
@Import(MockConfiguration.class)
@Slf4j
class AiStoreGatewayApplicationTests {

    private String ymlRead;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertTrue(true);

        //来自于 @TestConfiguration
        MockFoo bean = applicationContext.getBean(MockFoo.class);
        assertNotNull(bean);
        assertEquals("hello", bean.getName());
        
    }

    @Test
    void applicationArgumentsPopulated(@Autowired ApplicationArguments args) {
        assertThat(args.getOptionNames()).containsOnly("app.test");
        assertThat(args.getOptionValues("app.test")).containsOnly("one");
    }

}
