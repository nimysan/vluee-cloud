package com.vluee.cloud.commons.ddd.support.actuator;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import com.vluee.cloud.commons.ddd.support.event.publisher.DomainEventCompensationHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;

@Endpoint(id = "domain-events")
@AllArgsConstructor
public class DomainEventActuatorEndpoint {

    private final DomainEventRepository domainEventRepository;
    private final DomainEventCompensationHandler domainEventCompensationHandler;

    @ReadOperation
    public SimpleDomainEvent loadEvent(String id) {
        return domainEventRepository.load(new AggregateId(id));
    }

    @WriteOperation
    public void startCom(boolean active) {
        if (active) {
            domainEventCompensationHandler.active();
        } else {
            domainEventCompensationHandler.deactivate();
        }
    }
}
