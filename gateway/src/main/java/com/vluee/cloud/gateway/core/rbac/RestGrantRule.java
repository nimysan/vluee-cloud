package com.vluee.cloud.gateway.core.rbac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 *
 */
@ToString
@AllArgsConstructor
@Builder
@Data
public class RestGrantRule {

    private String method;

    private String url;

    private java.util.List<String> roles;

}
