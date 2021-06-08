package com.vluee.cloud.statistics.application.listeners;

import com.vluee.cloud.commons.ddd.annotations.event.DomainEventAction;
import com.vluee.cloud.commons.ddd.annotations.event.DomainEventBinding;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 监听hotel的相关事件， 并实时统计
 */
@AllArgsConstructor
@Slf4j
@DomainEventBinding
public class HotelStatisticsListener {
    private final StatisticsApplicationService applicationService;

    /**
     * 接听事件并写入统计数据
     */
//    @StreamListener(DomainEventClient.INPUT_TOPIC)
    @DomainEventAction
    public void test(String payload) {
        log.info("---fefefe {} --- ", payload);
        applicationService.getHotelGeneralStatistics().increment();
        applicationService.save();
    }

//    /**
//     * 接听事件并写入统计数据
//     */
//    @DomainEventListener
//    public void go(Object payload) {
//        log.info("---gogogo {} --- ", payload);
//        applicationService.getHotelGeneralStatistics().increment();
//        applicationService.save();
//    }
}
