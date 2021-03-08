package com.vluee.cloud.uams;

import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import com.vluee.cloud.commons.config.CQRSCommandConfig;
import com.vluee.cloud.commons.distributedlock.MutexLockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("com.vluee.cloud.uams"),
        @ComponentScan("com.vluee.cloud.commons.ddd")
})
@EnableJpaRepositories(
        basePackages = {"com.vluee.cloud.commons.ddd", "com.vluee.cloud.uams"}
)
@EntityScan(basePackages = {"com.vluee.cloud.uams", "com.vluee.cloud.commons.ddd"})
@Import({IdConfig.class, BaseAuditConfig.class, CQRSCommandConfig.class})
@Slf4j
public class UamsApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(UamsApplication.class, args);
    }

    @Autowired
    private MutexLockRepository mutexLockRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("class {}", mutexLockRepository);
        log.info("done .... done ... done ....");
    }


}
