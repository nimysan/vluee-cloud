package com.vluee.cloud.commons.ddd.support.infrastructure.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vluee.cloud.commons.ddd.support.event.exception.DomainEventReadException;
import com.vluee.cloud.commons.ddd.support.event.exception.DomainEventSerializeException;
import com.vluee.cloud.commons.ddd.support.event.serialize.DomainEventSerializer;

import java.io.Serializable;

/**
 * 基于Jackson实现的事件序列化和反序列化器
 *
 * @param <T>
 */
public class JacksonDomainEventSerializer<T> implements DomainEventSerializer<T> {

    private final ObjectMapper objectMapper;

    public JacksonDomainEventSerializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public String serialize(Serializable event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new DomainEventSerializeException(event, e);
        }
    }

    @Override
    public T read(String content, Class<T> klass) {
        try {
            return objectMapper.readValue(content, klass);
        } catch (JsonProcessingException e) {
            throw new DomainEventReadException(content, klass, e);
        }
    }
}
