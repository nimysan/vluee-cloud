package com.vluee.cloud.users.interfaces.query.rest.vo;

import com.vluee.cloud.users.core.user.domain.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailsVO {

    public static final UserDetailsVO from(User uamsUser) {
        UserDetailsVO vo = new UserDetailsVO();
        vo.setUsername(uamsUser.getUserName()).setEnable(true).setExpired(false).setLocked(false).setCredentialsNonExpired(false).setPassword("");
//        vo.setAuthorities();
        return vo;
    }

    private String password;
    private String username;
    private boolean enable;
    private boolean locked;
    private boolean expired;
    private boolean CredentialsNonExpired;
    private Collection<String> authorities = new ArrayList<>(2);

}
