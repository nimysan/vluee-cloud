package com.vluee.cloud.commons.common.uams;

import cn.hutool.json.JSONUtil;
import com.vluee.cloud.commons.common.uams.exception.UamsInvalidResourceDescriptorException;

/**
 * 声明这是一个被UAMS保护的资源
 */
public interface ResourceDescriptor {

    public String getName();
    public String getDescription();

    /**
     * 将资源描述符转化为json字符串
     *
     * @return
     */
    default public String toJsonStr() {
        try {
            return JSONUtil.toJsonStr(this);
        } catch (Exception e) {
            throw new UamsInvalidResourceDescriptorException("非法资源定义", e);
        }
    }
}
