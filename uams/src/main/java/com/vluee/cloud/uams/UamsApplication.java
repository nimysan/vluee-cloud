package com.vluee.cloud.uams;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("com.vluee.cloud.uams")
})
@Import({IdConfig.class, BaseAuditConfig.class})
public class UamsApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(UamsApplication.class, args);
    }

    @Autowired
    private CRoleRepository cRoleRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        CRole role = new CRole(AggregateId.generate(), "test1");
        cRoleRepository.save(role);

        role = new CRole(AggregateId.generate(), "super");
        cRoleRepository.save(role);

        role.addPermission(new AggregateId("p1"));
        role.addPermission(new AggregateId("p2"));
        cRoleRepository.save(role);
    }
}
