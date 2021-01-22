package com.vluee.cloud.users.core.user.service;

import com.vluee.cloud.users.core.user.domain.User;
import org.junit.jupiter.api.Test;

class UamsServiceImplTest {

    /**
     *
     */
    private UserService userService;


    @Test
    public void testCreatePassword() {
        User test = userService.createUser("test");
        test.createPassword("p1");
    }
}