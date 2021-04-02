package com.vluee.cloud.statistics.interfaces.write.listeners;

import com.vluee.cloud.commons.ddd.annotations.event.KafkaDomainEventListener;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SampleStatisticsListener {
    @Resource
    private InfluxDB influxDB;  //注入influxDB

    @KafkaDomainEventListener
    public void processMessage(String content) {
        try {
            influxDB.setDatabase("mydb");
            //新建一个Point,指定表名，和tag以及field
            //由于是链式调用可以增加多个Tag和Field
            Point point = Point.measurement("domain_event_count")
                    .tag("host", "server03")
                    .tag("region", "zh-east")
                    .addField("value", 1.0)
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .build();
            influxDB.write(point);
        } catch (Exception e) {
            e.printStackTrace();
//            return QueryResult.Result.error();
        }
//        return QueryResult
        log.info("---- hello --- " + content);
    }
}
