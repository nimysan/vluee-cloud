package com.vluee.cloud.auth.core.user.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@AggregateRoot
@NoArgsConstructor
public class UserAccount extends BaseAggregateRoot {

    public UserAccount(AggregateId aggregateId, UserData userData, LoginPassword loginPassword) {
        this.userData = userData;
        this.loginPassword = loginPassword;
        this.aggregateId = aggregateId;
    }

    @Embedded
    private UserData userData;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    private LoginPassword loginPassword;

}
