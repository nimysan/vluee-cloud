package com.vluee.cloud.users.core.user.service;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * 基于UUID的用于测试用途的ID生成器，不建议在生产使用。
 *
 * <br/>
 *
 * <strong>不适用分布式部署</strong>
 */
public class UamsIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return UUID.randomUUID().toString();
    }
}
