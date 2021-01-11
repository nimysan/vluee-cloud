package com.vluee.cloud.commons.common;

public interface AiStoreConstants {

    /**
     * 通用前缀
     */
    public static final String NORMAL_COMMON_KEY_PREFIX = "com.vluee.cloud.pms";

    public static final String SERVICE_NAME_SPACE = "saas";

    /**
     * 授权： 角色和授权码映射关系得主键
     */
    public static final String AUTH_ROLE_AUTHORITY_MAP = NORMAL_COMMON_KEY_PREFIX + ":auth:role_authority_map";

    /**
     * API/MENU/BUTTON
     */
    public static enum UamsPermissionType {
        API(1), MENU(2), BUTTON(3);

        private int typeValue;

        UamsPermissionType(int typeValue) {
            this.typeValue = typeValue;
        }

        public int getTypeValue() {
            return typeValue;
        }
    }

    public static class KafkaTopics {
        /**
         * Uams Resource/Role访问关系变化通知
         */
        public static final String TOPIC_UAMS_RESOURCE_ROLE = "uamsResourceRoleTopic";
    }


    /**
     * 注册在nacos中的服务名称常量
     */
    public static class Services {
        public static final String PREFIX = "-";
        public static final String USER = SERVICE_NAME_SPACE + PREFIX + "users";
        public static final String GATEWAY = SERVICE_NAME_SPACE + PREFIX + "gateway";
        public static final String TENANT = SERVICE_NAME_SPACE + PREFIX + "tenants";
    }


}
