package com.vluee.cloud.commons.ddd.support.event;

import cn.hutool.extra.spring.SpringUtil;
import com.vluee.cloud.commons.common.spring.SpringContextUtils;

/**
 * 构造domain event publisher的工厂
 */
public class DomainEventPublisherFactory {

    private static DomainEventPublisher STATIC_DOMAIN_EVENT_PUBLISHER;

    public static synchronized DomainEventPublisher getPublisher() {
        if (STATIC_DOMAIN_EVENT_PUBLISHER == null) {
            STATIC_DOMAIN_EVENT_PUBLISHER = SpringUtil.getBean(DomainEventPublisher.class);
        }
        return STATIC_DOMAIN_EVENT_PUBLISHER;
    }

}
