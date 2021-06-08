package com.vluee.cloud.commons.ddd.annotations.event;

import com.vluee.cloud.commons.ddd.support.infrastructure.events.stream.DomainEventClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

import java.lang.annotation.*;

/**
 * 搭配 DomainEventAction 一起使用。
 *
 * <pre>
 *     @DomainEventBinding
 *     public class HotelStatisticsListener {
 *          private final StatisticsApplicationService applicationService;
 *
 *          @DomainEventAction
 *          public void test(String payload) {
 *              log.info("---fefefe {} --- ", payload);
 *              applicationService.getHotelGeneralStatistics().increment();
 *              applicationService.save();
 *          }
 *
 *      }
 * </pre>
 *
 * @see DomainEventAction
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableBinding(DomainEventClient.class)
public @interface DomainEventBinding {
}
