package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import lombok.Getter;
import lombok.ToString;

/**
 * permission的操作动词
 */
@ValueObject
@ToString
public class Operation {

    @Getter
    private String verb;

    public Operation(String verb) {
        this.verb = verb;
    }

}
