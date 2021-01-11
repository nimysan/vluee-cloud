package com.vluee.cloud.users.interfaces.rest;

import com.vluee.cloud.users.core.user.service.UserService;
import com.vluee.cloud.users.interfaces.rest.vo.UserDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    UserDetailsVO loadUserByUsername(@PathVariable String username) {
        return UserDetailsVO.from(userService.loadUserByUserName(username));
    }

    @PostMapping
    void register(@RequestParam String username, @RequestParam String rawPassword) {
        userService.createUser(username);
    }


    @PutMapping("{username}")
    void changeNickName(@PathVariable String username,  @RequestParam String nickName){
        userService.changeNickName(username, nickName);
    }

}
