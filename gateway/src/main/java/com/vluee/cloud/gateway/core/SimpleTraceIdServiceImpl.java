package com.vluee.cloud.gateway.core;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * 请不要在分布式环境下使用该Long Id生产器, 可能会导致Id重复.
 *
 * 分布式环境下使用 redis/id 等解决方案
 *
 * @author sean.ye
 */
@Component
public class SimpleTraceIdServiceImpl implements TraceIdService {
    private static volatile int Guid = 100;

    @Override
    public synchronized Long nextId() {
        SimpleTraceIdServiceImpl.Guid += 1;
        long now = System.currentTimeMillis();
        //获取4位年份数字
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        //获取时间戳
        String time = dateFormat.format(now);
        String info = now + "";
        //获取三位随机数
        //int ran=(int) ((Math.random()*9+1)*100);
        //要是一段时间内的数据量过大会有重复的情况，所以做以下修改
        int ran = 0;
        if (SimpleTraceIdServiceImpl.Guid > 999) {
            SimpleTraceIdServiceImpl.Guid = 100;
        }
        ran = SimpleTraceIdServiceImpl.Guid;

        return Long.parseLong(time + info.substring(2) + ran);
    }
}