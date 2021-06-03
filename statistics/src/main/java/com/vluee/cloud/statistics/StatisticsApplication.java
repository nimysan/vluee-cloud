package com.vluee.cloud.statistics;

import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import com.vluee.cloud.commons.config.CQRSCommandConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("com.vluee.cloud.statistics")
})
@EnableSwagger2
@Import({BaseAuditConfig.class, IdConfig.class, CQRSCommandConfig.class})
@Slf4j
public class StatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }

}
