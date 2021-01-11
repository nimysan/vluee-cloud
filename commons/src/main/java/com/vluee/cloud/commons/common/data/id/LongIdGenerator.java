package com.vluee.cloud.commons.common.data.id;

public interface LongIdGenerator {

    /**
     * Hibernate ID Generator label
     */
    public static final String ID_GENERATOR_NAME = "UUID-ID-GENERATOR";

    /**
     * Delegate Id Generator
     */
    public static final String DISTRIBUTED_ID_GENERATOR_CLASS_NAME = "cn.jzdata.aistore.common.data.id.HibernateIdGenerator";

    /**
     * 产生下一个ID
     *
     * @return
     */
    public long nextId();

    public String nextIdStr();

}
