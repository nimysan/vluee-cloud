package com.vluee.cloud.uams.spring;

import com.vluee.cloud.commons.ddd.support.domain.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.JpaDomainEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa", "com.vluee.cloud.uams.infrastructure.repo.jpa", "com.vluee.cloud.uams.readmodel.jpa"})
@Slf4j
public class JPAConfig {

    @Bean
    public DomainEventRepository setup() {
        log.info("--- {} --- ", JpaDomainEventRepository.class);
        return new JpaDomainEventRepository();
    }
}
