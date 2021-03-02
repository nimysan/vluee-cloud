package com.vluee.cloud.uams.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.vluee.cloud.commons.ddd", "com.vluee.cloud.uams.infrastructure.repo.jpa", "com.vluee.cloud.uams.readmodel.jpa"}
)
@EntityScan(basePackages = {"com.vluee.cloud.uams", "com.vluee.cloud.commons.ddd"})
@Slf4j
public class JPAConfig {

}
