package com.vluee.cloud.statistics.application.listeners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 监听hotel的相关事件， 并实时统计
 */
@AllArgsConstructor
@EnableBinding(Sink.class)
@Slf4j
public class HotelStatisticsListener {
    private final StatisticsApplicationService applicationService;

    /**
     * 接听事件并写入统计数据
     */
    @StreamListener(Sink.INPUT)
    public void test(Object payload) {
        log.info("---fefefe {} --- ", payload);
        applicationService.getHotelGeneralStatistics().increment();
        applicationService.save();
    }

    /**
     * 接听事件并写入统计数据
     */
    @StreamListener(Sink.INPUT)
    public void go(Object payload) {
        log.info("---gogogo {} --- ", payload);
        applicationService.getHotelGeneralStatistics().increment();
        applicationService.save();
    }
}
