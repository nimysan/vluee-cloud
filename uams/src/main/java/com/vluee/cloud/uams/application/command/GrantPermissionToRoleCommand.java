package com.vluee.cloud.uams.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class GrantPermissionToRoleCommand {
    private Long roleId;
    private Long permissionId;
}
