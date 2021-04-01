package com.vluee.cloud.statistics.spring;

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
                .apiBasePackage("com.vluee.cloud.statistics.interfaces")
                .title("统计分析服务")
                .description("统计分析")
                .contactName("234aini@163.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
