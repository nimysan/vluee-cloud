package com.vluee.cloud.tenants.spring;

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
                .apiBasePackage("cn.jzdata.aistore.tenant.interfaces")
                .title("mall搜索系统")
                .description("mall搜索相关接口文档")
                .contactName("macro")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
