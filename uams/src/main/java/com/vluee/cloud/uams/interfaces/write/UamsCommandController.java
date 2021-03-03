package com.vluee.cloud.uams.interfaces.write;

import com.vluee.cloud.commons.cqrs.command.Gate;
import com.vluee.cloud.uams.application.command.AddRoleCommand;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
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
}
