package com.vluee.cloud.statistics.interfaces.write.listeners;

import com.vluee.cloud.commons.ddd.annotations.event.KafkaDomainEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SampleStatisticsListener {

    @KafkaDomainEventListener
    public void processMessage(String content) {
        log.info("---- hello --- " + content);
    }
}
