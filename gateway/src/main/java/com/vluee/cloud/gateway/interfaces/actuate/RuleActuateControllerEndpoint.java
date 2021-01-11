package com.vluee.cloud.gateway.interfaces.actuate;

import com.vluee.cloud.commons.common.AiStoreConstants;
import com.vluee.cloud.gateway.core.security.GatewayAuthorizationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestControllerEndpoint(id = "as-gateway")
@Slf4j
public class RuleActuateControllerEndpoint {

    private final GatewayAuthorizationManager authorizationManager;

    public RuleActuateControllerEndpoint(GatewayAuthorizationManager manager) {
        this.authorizationManager = manager;
    }

    @Autowired
    private KafkaTemplate<String, String> template;

    @PostMapping("rules/refresh")
    public Mono<Void> refreshRules() {
        return authorizationManager.updateAuthorizations().then();
    }

    @GetMapping("rules")
    public Flux<String> rules() {
        return authorizationManager.showRules();
    }

    /**
     * @param opType 1 add/update 2 delete 3 refresh all (ruleId can be anything)
     * @param ruleId
     * @return
     */
    @GetMapping("rules/test")
    public Mono<Void> sendKafkaMessage(@RequestParam String opType, @RequestParam String ruleId) {
        template.send(AiStoreConstants.KafkaTopics.TOPIC_UAMS_RESOURCE_ROLE, opType + ":" + ruleId);
        return Mono.empty();
    }

}
