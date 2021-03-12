package com.vluee.cloud.uams.interfaces.write;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.command.Gate;
import com.vluee.cloud.uams.application.command.*;
import com.vluee.cloud.uams.core.permission.domain.PermissionType;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@AllArgsConstructor
public class UamsCommandController {

    private final Gate commandGate;

    @PostMapping("/roles")
    public void addRole(@RequestParam String roleName) {
        AddRoleCommand addRoleCommand = AddRoleCommand.builder().roleName(roleName).build();
        commandGate.dispatch(addRoleCommand);
    }

    @PostMapping("/grants/roles/{roleId}/apipermissions/{apiPermissionId}")
    public void roleApiGrant(@PathVariable String roleId, @PathVariable String apiPermissionId) {
        GrantPermissionToRoleCommand grantPermissionToRoleCommand = new GrantPermissionToRoleCommand(new AggregateId(roleId), new AggregateId(apiPermissionId), PermissionType.API);
        commandGate.dispatch(grantPermissionToRoleCommand);
    }

    @PostMapping("/grants/users/{userId}/roles/{roleId}")
    public void grantUserRole(@PathVariable String userId, @PathVariable String roleId) {
        commandGate.dispatch(new GrantRoleToUserCommand(new AggregateId(userId), new AggregateId(roleId)));
    }

    @PostMapping("/grants/usergroups/{userGroupId}/roles/{roleId}")
    public void grantUserGroupRole(@PathVariable String userGroupId, @PathVariable String roleId) {
        commandGate.dispatch(new GrantRoleToUserGroupCommand(new AggregateId(userGroupId), new AggregateId(roleId)));
    }

    //REST 命名规则如何？
    @PostMapping("/resources/apis")
    public void registerApi(@RequestParam String verb, @RequestParam String url) {
        AddApiCommand command = AddApiCommand.builder().verb(verb).url(url).build();
        commandGate.dispatch(command);
    }


    @PostMapping("/users")
    public void registerUser(@RequestParam String username, @RequestParam String password) {//如何防止明文密码传输?
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(username, password);
        commandGate.dispatch(registerUserCommand);
    }

    @PostMapping("/usergroups")
    public void createUserGroup(@RequestParam String groupName) {
        CreateUserGroupCommand createUserGroupCommand = new CreateUserGroupCommand(groupName);
        commandGate.dispatch(createUserGroupCommand);
    }

    @PostMapping("/users/{userId}/usergroups/{groupId}")
    public void userJoinGroup(@PathVariable String userId, @PathVariable String groupId) {
        UserJoinGroupCommand userJoinGroupCommand = new UserJoinGroupCommand(new AggregateId(userId), new AggregateId(groupId));
        commandGate.dispatch(userJoinGroupCommand);
    }
}
