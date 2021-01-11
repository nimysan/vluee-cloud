package com.vluee.cloud.users.core.user.service;

import com.vluee.cloud.users.core.user.domain.User;

/**
 * 用户/用户组/角色/资源 管理服务
 * <p>
 * Domain Service
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param userCode
     * @return
     */
    User createUser(String userCode);


    /**
     * 获取用户详情
     *
     * @param username
     * @return
     */
    User loadUserByUserName(String username);

    /**
     * 修改用户昵称
     *
     * @param username
     * @param nickName
     */
    void changeNickName(String username, String nickName);

    /**
     * 将相关账号设定为锁定
     *
     * @param username
     */
    void lockUser(String username);
}
