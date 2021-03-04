package com.vluee.cloud.uams.interfaces.query.rest;

import com.vluee.cloud.commons.ddd.support.infrastructure.events.SimpleDomainEventPublisher;
import com.vluee.cloud.uams.application.service.UamsCommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UamsController {

    private final UamsCommandHandler uamsCommandHandler;
    private final SimpleDomainEventPublisher simpleDomainEventPublisher;

    @GetMapping("final")
    public void test() {
//        simpleDomainEventPublisher.handle();
    }

}
