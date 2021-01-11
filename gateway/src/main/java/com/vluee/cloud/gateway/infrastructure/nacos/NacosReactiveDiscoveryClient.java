package com.vluee.cloud.gateway.infrastructure.nacos;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import reactor.core.publisher.Flux;

public class NacosReactiveDiscoveryClient implements ReactiveDiscoveryClient {

    /**
     * Nacos Discovery Client Description.
     */
    public static final String DESCRIPTION = "Spring Cloud Nacos Reactive Discovery Client";


    private NacosServiceDiscovery nacosServiceDiscovery;

    public NacosReactiveDiscoveryClient(NacosServiceDiscovery nacosServiceDiscovery) {
        this.nacosServiceDiscovery = nacosServiceDiscovery;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public Flux<ServiceInstance> getInstances(String serviceId) {
        try {
            return Flux.fromIterable(nacosServiceDiscovery.getInstances(serviceId));
        } catch (NacosException e) {
            e.printStackTrace();
            return Flux.empty();
        }

    }

    @Override
    public Flux<String> getServices() {
        try {
            return Flux.fromIterable(nacosServiceDiscovery.getServices());
        } catch (NacosException e) {
            e.printStackTrace();
            return Flux.empty();
        }
    }
}
