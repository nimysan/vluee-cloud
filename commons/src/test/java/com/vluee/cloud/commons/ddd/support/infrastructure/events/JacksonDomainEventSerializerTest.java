package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JacksonDomainEventSerializerTest {

    @Test
    public void testSerializeAggregateId() {
        JacksonDomainEventSerializer<AggregateId> domainEventSerializer = new JacksonDomainEventSerializer<>();
        AggregateId aggregateId = new AggregateId("test");

        String serialize = domainEventSerializer.serialize(aggregateId);
        AggregateId restoreOne = domainEventSerializer.read(serialize, AggregateId.class);

        Assertions.assertEquals(aggregateId, restoreOne);
        Assertions.assertEquals("test", restoreOne.getId());
    }
}