package com.vluee.cloud.uams.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserGroupCommand {
    private String groupName;
}
