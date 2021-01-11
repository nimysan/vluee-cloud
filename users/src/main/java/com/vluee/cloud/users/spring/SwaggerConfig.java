package com.vluee.cloud.users.spring;

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
                .apiBasePackage("cn.jzdata.aistore.user.interfaces.rest")
                .title("AS用户中心")
                .description("AS用户中心相关接口文档")
                .contactName("234aini@163.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}