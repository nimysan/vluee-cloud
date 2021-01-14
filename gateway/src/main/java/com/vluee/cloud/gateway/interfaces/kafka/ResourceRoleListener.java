package com.vluee.cloud.gateway.interfaces.kafka;

import com.vluee.cloud.commons.common.ServiceConstants;
import com.vluee.cloud.gateway.core.events.AddResourceRoleEvent;
import com.vluee.cloud.gateway.core.events.DeleteResourceRoleEvent;
import com.vluee.cloud.gateway.core.events.RefreshAllEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 接收kafka的通知， 刷新Resource/Role权限配置
 */
@Slf4j
@Component
public class ResourceRoleListener implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @KafkaListener(topics = ServiceConstants.KafkaTopics.TOPIC_UAMS_RESOURCE_ROLE)
    public void handleMessage(ConsumerRecord<String, String> record) throws Exception {
        log.info(" --- {} --- ", record.value());
        String value = record.value();
        String[] split = value.split(":");
        if ("1".equalsIgnoreCase(split[0])) {
            //add or update
            applicationContext.publishEvent(new AddResourceRoleEvent(split[1]));
        } else if ("2".equalsIgnoreCase(split[0])) {
            // delete
            applicationContext.publishEvent(new DeleteResourceRoleEvent(split[1]));
        } else if ("3".equals(split[0])) {
            //refresh all
            applicationContext.publishEvent(new RefreshAllEvent(""));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
