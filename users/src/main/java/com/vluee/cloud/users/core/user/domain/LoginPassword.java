package com.vluee.cloud.users.core.user.domain;

import com.vluee.cloud.commons.common.data.AuditAware;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 登录密码维护
 */
@Entity
@Table(name = "login_password")
public class LoginPassword extends AuditAware {

    @Id
    private Long id;

    @Column(name = "password_text", nullable = false)
    private String passwordText;

    @Column(name = "active_status", nullable = false)
    private boolean activeStatus;
}
