package com.vluee.cloud.uams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AistoreUamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AistoreUamsApplication.class, args);
    }

}
