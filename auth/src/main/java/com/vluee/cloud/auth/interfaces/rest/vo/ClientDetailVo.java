package com.vluee.cloud.auth.interfaces.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClientDetailVo {

    private String clientId;

    private String clientName;

    private String resourceIds;

    private String scope;

    private String authorizedGrantTypes;
}
