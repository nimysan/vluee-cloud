package com.vluee.cloud.uams.core.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor // for jpa
public class RestApi {
    @Getter
    private String verb;
    @Getter
    private String url;
}
