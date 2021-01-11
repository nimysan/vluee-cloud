package com.vluee.cloud.commons.common.data.id;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * 从当前JVM获取ID generator并使用generator去生成ID
 */
@Slf4j
public class HibernateIdGenerator implements IdentifierGenerator {

    private static LongIdGenerator delegateIdGenerator;

    public HibernateIdGenerator() {
    }

    public static Serializable nextId() {
        if (delegateIdGenerator == null) {
            delegateIdGenerator = locateIdGeneratorFromContext();
        }
        return delegateIdGenerator.nextId();
    }

    public static LongIdGenerator locateIdGeneratorFromContext() {
        return SpringUtil.getApplicationContext().getBean(LongIdGenerator.class);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return nextId();
    }
}
