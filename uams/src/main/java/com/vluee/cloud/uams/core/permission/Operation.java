package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * permission的操作动词
 */
@ValueObject
@ToString
@Embeddable
@NoArgsConstructor
public class Operation {

    @Column(name = "operator", nullable = false, length = 64)
    @Getter
    private String operator;

    public Operation(String operator) {
        this.operator = operator;
    }

    public static final Operation API_EXECUTE_OPERATION = new Operation("exe");


}
