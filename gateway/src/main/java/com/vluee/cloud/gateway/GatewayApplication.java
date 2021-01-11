package com.vluee.cloud.gateway;

import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Hooks;

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("com.vluee.cloud.gateway")
})
@Import({IdConfig.class, BaseAuditConfig.class})
public class GatewayApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(GatewayApplication.class, args);
    }

}
