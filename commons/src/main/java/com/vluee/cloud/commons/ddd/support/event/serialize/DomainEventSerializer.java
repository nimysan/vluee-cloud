package com.vluee.cloud.commons.ddd.support.event.serialize;

import java.io.Serializable;

public interface DomainEventSerializer<T> {

    String serialize(Serializable event);

    T read(String content, Class<T> klass);
}
