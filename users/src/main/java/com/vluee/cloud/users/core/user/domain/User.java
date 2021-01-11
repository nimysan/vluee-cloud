package com.vluee.cloud.users.core.user.domain;

import com.vluee.cloud.commons.common.data.AuditAware;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class User extends AuditAware {

    @Deprecated
    public User() {
    }

    public User(String username) {
        this.userName = username;
    }

    @Id
    private Long id;

    /**
     * 用户说明
     */
    private String description;

    /**
     * 账号昵称
     */
    private String nickName;

    /**
     *
     */
    @Column(length = 64, nullable = false, unique = true)
    private String userName;

    /**
     * 用户是否启用
     */
    private boolean enable;

    /**
     * 用户是否锁
     */
    private boolean locked;

    /**
     * 用户是否过期
     */
    private boolean expired;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isExpired() {
        return expired;
    }
}
