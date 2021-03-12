package com.vluee.cloud.auth.applications.service;

import com.vluee.cloud.auth.core.user.domain.UserAccount;
import com.vluee.cloud.auth.core.user.domain.UserAccountFactory;
import com.vluee.cloud.auth.core.user.domain.UserAccountRepository;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import lombok.AllArgsConstructor;

@ApplicationService
@AllArgsConstructor
public class UserRegisterService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountFactory userAccountFactory;

    /**
     * 根据uams得用户信息生成Saas账号信息
     *
     * @param uamsUserId
     * @param username
     * @param password
     */
    public void createSaasUser(String uamsUserId, String username, String password) {
        UserAccount user = userAccountFactory.createUser(uamsUserId, username, password);
        userAccountRepository.save(user);
    }
}
