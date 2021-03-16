package com.vluee.cloud.auth.interfaces.write;

import com.vluee.cloud.auth.applications.command.UserAccountCreateCommand;
import com.vluee.cloud.commons.cqrs.command.Gate;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Transactional
public class UserAccountController {

    private final Gate gate;

    /**
     * @param username 用户名
     * @param password 密码明文
     */
    @PostMapping("/user-accounts")
    public void createAccount(@RequestParam String userId, @RequestParam String username, @RequestParam String password) {
        gate.dispatch(new UserAccountCreateCommand(userId, username, password));
    }
}
