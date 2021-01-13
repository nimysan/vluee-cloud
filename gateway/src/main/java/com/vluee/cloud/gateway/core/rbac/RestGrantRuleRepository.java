package com.vluee.cloud.gateway.core.rbac;

import java.util.Set;

public interface RestGrantRuleRepository {

    /**
     * 所有规则得keys
     *
     * @return
     */
    public Set<String> ruleKeys();

    /**
     * 以逗号为分隔符得角色列表字符串
     *
     * @param key
     * @return
     */
    public String getRoleDefinition(String key);

}
