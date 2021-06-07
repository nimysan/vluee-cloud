package com.vluee.cloud.orgs.spring;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RealtimeCollector {
    public String output = "vluee-cloud-realtimecollector";

    @Output(RealtimeCollector.output)
    MessageChannel output();
}
