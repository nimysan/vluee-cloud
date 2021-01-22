package com.vluee.cherry.demo.spring;

import com.vluee.cloud.commons.common.doc.SwaggerProperties;
import com.vluee.cloud.commons.config.BaseSwaggerConfig;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.vluee.cherry.demo.interfaces")
                .title("无业务意义的接口测试服务(无需认证)")
                .description("用于测试接口返回和调用格式验证，不包含任何实际业务逻辑")
                .contactName("234aini@163.com")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
