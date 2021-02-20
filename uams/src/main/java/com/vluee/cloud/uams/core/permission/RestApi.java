package com.vluee.cloud.uams.core.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class RestApi {
    private String verb;
    private String url;
}
