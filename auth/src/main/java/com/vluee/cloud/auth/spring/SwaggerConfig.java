package com.vluee.cloud.auth.spring;

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
                .apiBasePackage("com.vluee.cloud.auth.interfaces")
                .title("认证服务")
                .description("基于OAuth2理论的用户认证服务")
                .contactName("234aini@163.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}