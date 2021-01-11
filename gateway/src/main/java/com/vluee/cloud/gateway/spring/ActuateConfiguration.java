package com.vluee.cloud.gateway.spring;

import com.vluee.cloud.gateway.interfaces.actuate.RuleActuateControllerEndpoint;
import com.vluee.cloud.gateway.core.security.GatewayAuthorizationManager;
import com.vluee.cloud.gateway.core.security.GatewaySecurityConfig;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

//@Configuration
@AutoConfigureAfter({GatewaySecurityConfig.class})
public class ActuateConfiguration {
    @Bean
    @ConditionalOnClass(Health.class)
    public Object ruleActuator(GatewayAuthorizationManager authorizationManager) {
        return new RuleActuateControllerEndpoint(authorizationManager);
    }
}
