package com.vluee.cloud.orgs.spring;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StreamClient {
    public String output = "vluee-cloud-pgone";

    @Output(StreamClient.output)
    MessageChannel outout();
}
