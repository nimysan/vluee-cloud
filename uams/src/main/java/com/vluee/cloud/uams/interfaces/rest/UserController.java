package com.vluee.cloud.uams.interfaces.rest;

import com.vluee.cloud.uams.core.uams.service.UamsService;
import com.vluee.cloud.uams.interfaces.rest.vo.UserDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UamsService uamsService;

    @GetMapping("{username}")
    UserDetailsVO loadUserByUsername(@PathVariable String username) {
        return UserDetailsVO.from(uamsService.loadUserByUserName(username));
    }

    @PostMapping
    void register(@RequestParam String tenantId, @RequestParam String username, @RequestParam String rawPassword) {
        uamsService.createUser(tenantId, username);
    }

    @PutMapping("{userId}/groups/{groupId}")
    void assignGroup(@PathVariable String userId, @PathVariable String groupId) {
        uamsService.assignUserToGroup(userId, groupId);
    }
}
