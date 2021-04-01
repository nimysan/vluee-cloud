package com.vluee.cloud.statistics.interfaces.write.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SampleStatisticsListener {

    @KafkaListener(topics = "someTopic", groupId = "group.1")
    public void processMessage(String content) {
        log.info("---- hello --- " + content);
    }
}
