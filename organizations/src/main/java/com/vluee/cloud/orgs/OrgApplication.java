package com.vluee.cloud.orgs;

import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import com.vluee.cloud.commons.config.CQRSCommandConfig;
import com.vluee.cloud.commons.config.DomainEventConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication

@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("com.vluee.cloud.orgs"),
        @ComponentScan("com.vluee.cloud.commons.ddd")
})
@EnableJpaRepositories(
        basePackages = {"com.vluee.cloud.commons.ddd", "com.vluee.cloud.orgs"}
)
@EntityScan(basePackages = {"com.vluee.cloud.orgs", "com.vluee.cloud.commons.ddd"})
@Import({IdConfig.class, BaseAuditConfig.class, CQRSCommandConfig.class, DomainEventConfig.class})
@Slf4j
public class OrgApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrgApplication.class, args);
    }

}
