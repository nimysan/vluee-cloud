package com.vluee.cloud.uams.core.resources.domain;

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
public class ResourceOperation {

    @Column(name = "operator", nullable = false, length = 64)
    @Getter
    private String operator;

    public ResourceOperation(String operator) {
        this.operator = operator;
    }

    public static final ResourceOperation API_EXECUTE_RESOURCE_OPERATION = new ResourceOperation("exe");


}
