package com.vluee.cloud.auth;

import com.vluee.cloud.auth.applications.service.UserRegisterService;
import com.vluee.cloud.auth.core.client.service.ClientManageService;
import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import com.vluee.cloud.commons.config.CQRSCommandConfig;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("com.vluee.cloud.auth")
})
@Import({IdConfig.class, BaseAuditConfig.class, CQRSCommandConfig.class})
@AllArgsConstructor
public class AuthServerApplication implements ApplicationRunner {

    private final UserRegisterService userRegisterService;

    private final ClientManageService clientManageService;

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        clientManageService.registerClient("gateway", "hello");

        userRegisterService.createSaasUser("u1id", "u1", "123456");
        userRegisterService.createSaasUser("u2id", "u2", "123456");
        userRegisterService.createSaasUser("u3id", "u3", "123456");
    }
}
