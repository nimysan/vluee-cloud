package com.vluee.cloud.users.interfaces.command.rest;

import com.vluee.cloud.users.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserCommandController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    void register(@RequestParam String username, @RequestParam String rawPassword) {
        userService.createUser(username);
    }

    @PutMapping("/users/{username}")
    void changeNickName(@PathVariable String username, @RequestParam String nickName) {
        userService.changeNickName(username, nickName);
    }

    @PutMapping("/lockUsers/{username}")
    void lockUser(@PathVariable String username) {
        userService.lockUser(username);
    }

}
