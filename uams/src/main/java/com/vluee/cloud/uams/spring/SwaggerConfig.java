package com.vluee.cloud.uams.spring;

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
                .apiBasePackage("com.vluee.cloud.uams.interfaces")
                .title("统一授权维护中心")
                .description("管理资源和角色授权")
                .contactName("234aini@163.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}