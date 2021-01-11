package com.vluee.cloud.commons.common.data.id;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class SnowflakeIdGenerator implements LongIdGenerator {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long workerId = 0; //为终端ID

    private long datacenterId = 1; //数据中心ID

    private Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

    @PostConstruct
    public void init() {
        workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        log.info("当前机器的workId:{}", workerId);
    }

    public synchronized long snowflakeId() {
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId) {
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

    @Override
    public long nextId() {
        return this.snowflakeId();
    }

    @Override
    public String nextIdStr() {
        return Long.toString(nextId());
    }
}

