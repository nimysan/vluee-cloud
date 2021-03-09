package com.vluee.cloud.commons.cqrs.command.handler.spring;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Method;

@ToString
@AllArgsConstructor
public class SpringCommandHandler {
    @Getter
    private final Class<?> eventType;
    private final String beanName;
    private final Method method;
    private final BeanFactory beanFactory;

    public boolean canHandle(Object command) {
        return eventType.isAssignableFrom(command.getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpringCommandHandler that = (SpringCommandHandler) o;
        return Objects.equal(eventType, that.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventType);
    }

    public Object handle(Object command) {
        try {
            Object bean = beanFactory.getBean(beanName);
            return method.invoke(bean, command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
