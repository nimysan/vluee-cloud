package com.vluee.cloud.auth.core.user.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserAccountFactory {

    private final LoginPasswordFactory loginPasswordFactory;

    public UserAccount createUser(String userId, String username, String password) {
        LoginPassword loginPassword = loginPasswordFactory.createLoginPassword(password);
        UserAccount userAccount = new UserAccount(AggregateId.generate(), new UserData(userId, username), loginPassword);
        return userAccount;
    }
}
