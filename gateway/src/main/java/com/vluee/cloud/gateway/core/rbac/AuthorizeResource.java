package com.vluee.cloud.gateway.core.rbac;

import cn.hutool.json.JSONUtil;
import com.vluee.cloud.commons.common.string.StringUtils;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 一个授权规则
 */
@Data
@Builder
@Slf4j
public class AuthorizeResource {

    private String id;

    private String verb;

    private String url;

    private List<String> authorities;

    private boolean permitAll = false;

    public String toJsonString() {
        return JSONUtil.toJsonStr(this);
    }

    public static AuthorizeResource buildFromJson(String json) {
        try {
            if (StringUtils.isNotEmpty(json)) {
                return JSONUtil.toBean(json, AuthorizeResource.class);
            }

        } catch (Exception e) {
            log.warn("There is invalid AuthorizeRule definition, the json is\r\n:  {}", json);
        }
        return null;
    }


}
