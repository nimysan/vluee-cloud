package com.vluee.cloud.auth.core.user.domain;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class LoginPasswordFactory {

    private final PasswordEncoder passwordEncoder;

    public LoginPassword createLoginPassword(String passwordText) {
        return new LoginPassword(passwordEncoder.encode(passwordText), fetchExpirationTime());
    }

    private Date fetchExpirationTime() {
        return DateUtil.date().offset(DateField.DAY_OF_MONTH, 27);
    }
}
