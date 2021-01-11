package com.vluee.cloud.users.interfaces.query.rest;

import com.vluee.cloud.users.core.user.service.UserService;
import com.vluee.cloud.users.interfaces.query.rest.vo.UserDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 供认证服务使用
     *
     * @param username
     * @return
     */
    @GetMapping("{username}")
    UserDetailsVO loadUserByUsername(@PathVariable String username) {
        return UserDetailsVO.from(userService.loadUserByUserName(username));
    }

}
