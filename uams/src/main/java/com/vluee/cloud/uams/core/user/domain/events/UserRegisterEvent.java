package com.vluee.cloud.uams.core.user.domain.events;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class UserRegisterEvent implements Serializable {
    @Getter
    private AggregateId userId;

    @Getter
    private String password;

    public UserRegisterEvent(AggregateId userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
