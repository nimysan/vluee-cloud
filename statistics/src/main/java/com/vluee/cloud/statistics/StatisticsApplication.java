package com.vluee.cloud.statistics;

import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import com.vluee.cloud.commons.config.CQRSCommandConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("com.vluee.cloud.statistics")
})
@EnableSwagger2
@Import({BaseAuditConfig.class, IdConfig.class, CQRSCommandConfig.class})
@Slf4j
public class StatisticsApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }

    @Autowired
    private Producer<Integer, Object> kafkaProducer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        kafkaProducer.send(new ProducerRecord("someTopic", "Love is love 1"));

        kafkaProducer.send(new ProducerRecord("someTopic", "Love is love 2"));

        kafkaProducer.send(new ProducerRecord("someTopic", "Love is love 3"));

        log.info("Finish send the message");
    }
}
