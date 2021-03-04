package com.vluee.cloud.commons.canonicalmodel.publishedlanguage;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class AggregateIdTest {

    @Test
    public void testHutoolJson() {
        AggregateId aggregateId = getAggregateId();
        String s = JSONUtil.toJsonStr(aggregateId);

        log.info("Final {}", s);
    }

    private AggregateId getAggregateId() {
        return new AggregateId("2");
    }

    @Test
    public void testJackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AggregateId aggregateId1 = getAggregateId();
        String s = objectMapper.writeValueAsString(aggregateId1);
        log.info(s);

        AggregateId aggregateId = objectMapper.readValue(s, AggregateId.class);
        Assertions.assertEquals(aggregateId1,aggregateId);
    }
}