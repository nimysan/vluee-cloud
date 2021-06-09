package com.vluee.cloud.searchbuilder;

import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import com.vluee.cloud.commons.config.CQRSCommandConfig;
import com.vluee.cloud.commons.config.DomainEventConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
@Import({BaseAuditConfig.class, IdConfig.class, CQRSCommandConfig.class, DomainEventConfig.class})
public class SearchBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchBuilderApplication.class, args);
    }

}
