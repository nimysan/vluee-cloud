package com.vluee.cloud.gateway.spring;

import com.vluee.cloud.commons.common.doc.SwaggerProperties;
import com.vluee.cloud.commons.config.BaseSwaggerConfig;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class GatewaySelfSwagger extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.vluee.cloud.gateway.interfaces.query.rest")
                .title("认证服务")
                .description("基于OAuth2理论的用户认证服务")
//                .contactName("234aini@163.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

    //    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        .description("example api")
//                        .title("example api")
//                        .version("1.0.0")
//                        .build())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.vluee.cloud.gateway.interfaces.query.rest"))
//                .paths(PathSelectors.any())
//                .build();
//    }
}
