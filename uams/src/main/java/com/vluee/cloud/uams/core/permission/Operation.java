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

    @Column(name = "operation_verb", nullable = false, length = 124)
    @Getter
    private String verb;

    public Operation(String verb) {
        this.verb = verb;
    }

}
