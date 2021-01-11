package com.vluee.cloud.tenants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("cn.jzdata.aistore.tenant")
})
@EnableSwagger2
public class AistoreTenantApplication {

    public static void main(String[] args) {
        SpringApplication.run(AistoreTenantApplication.class, args);
    }

}
