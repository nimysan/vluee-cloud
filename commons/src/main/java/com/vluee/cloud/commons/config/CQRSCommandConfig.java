package com.vluee.cloud.commons.config;

import com.vluee.cloud.commons.cqrs.command.Gate;
import com.vluee.cloud.commons.cqrs.command.handler.spring.SpringHandlersProvider;
import com.vluee.cloud.commons.cqrs.command.impl.RunEnvironment;
import com.vluee.cloud.commons.cqrs.command.impl.StandardGate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CQRSCommandConfig {

    @Bean
    @ConditionalOnMissingBean
    public Gate commandGate(RunEnvironment runEnvironment) {
        return new StandardGate(runEnvironment);
    }

    @Bean
    public RunEnvironment commandRunEnvironment(RunEnvironment.HandlersProvider handlersProvider) {
        return new RunEnvironment(handlersProvider);
    }

    @Bean
    public SpringHandlersProvider springHandlersProvider() {
        return new SpringHandlersProvider();
    }

}
