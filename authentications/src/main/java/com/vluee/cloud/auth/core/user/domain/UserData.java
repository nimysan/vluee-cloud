package com.vluee.cloud.auth.core.user.domain;

import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@ValueObject
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @Column
    private String userId;

    @Column
    private String username;

}
