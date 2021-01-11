package com.vluee.cloud.gateway.spring;

import com.vluee.cloud.gateway.infrastructure.nacos.NacosReactiveDiscoveryClient;
import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClientConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnDiscoveryHealthIndicatorEnabled;
import org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.discovery.health.DiscoveryClientHealthIndicatorProperties;
import org.springframework.cloud.client.discovery.health.reactive.ReactiveDiscoveryClientHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration related to service discovery when using Netflix Eureka.
 *
 * @author Tim Ysewyn
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
// @ConditionalOnBlockingDiscoveryEnabled
@ConditionalOnProperty(value = "spring.cloud.discovery.blocking.enabled", matchIfMissing = true)
@ConditionalOnNacosDiscoveryEnabled
//@AutoConfigureBefore({ SimpleDiscoveryClientAutoConfiguration.class,
//        CommonsClientAutoConfiguration.class })
@EnableConfigurationProperties
@AutoConfigureAfter({NacosDiscoveryAutoConfiguration.class,
        ReactiveCompositeDiscoveryClientAutoConfiguration.class})
@AutoConfigureBefore(ReactiveCommonsClientAutoConfiguration.class)
@ImportAutoConfiguration(NacosDiscoveryClientConfiguration.class)
public class NacosReactiveDiscoveryClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public NacosReactiveDiscoveryClient nacosReactiveDiscoveryClient(
            NacosServiceDiscovery nacosServiceDiscovery) {
        return new NacosReactiveDiscoveryClient(nacosServiceDiscovery);
    }

    @Bean
    @ConditionalOnClass(
            name = "org.springframework.boot.actuate.health.ReactiveHealthIndicator")
    @ConditionalOnDiscoveryHealthIndicatorEnabled
    public ReactiveDiscoveryClientHealthIndicator nacosReactiveDiscoveryClientHealthIndicator(
            NacosReactiveDiscoveryClient client,
            DiscoveryClientHealthIndicatorProperties properties) {
        return new ReactiveDiscoveryClientHealthIndicator(client, properties);
    }

}