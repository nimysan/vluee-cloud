package com.vluee.cloud.gateway.interfaces.query.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RestResourceForGrant {

    private String method;
    private String pattern;
}
