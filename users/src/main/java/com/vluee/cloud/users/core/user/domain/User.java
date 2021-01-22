package com.vluee.cloud.users.core.user.domain;

import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@GenericGenerator(name = LongIdGenerator.ID_GENERATOR_NAME, strategy = LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
public class User extends AuditAware {

    @Deprecated
    public User() {
    }

    public User(String username) {
        this.userName = username;
    }

    @Id
    @GeneratedValue(generator = LongIdGenerator.ID_GENERATOR_NAME)
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
     * 全局唯一的可读的账号ID，与数据库ID作为区别
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

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void createPassword(String password) {

    }
}
