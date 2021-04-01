package com.vluee.cloud.tenants.application.commands;

import com.vluee.cloud.commons.cqrs.annotations.Command;
import lombok.AllArgsConstructor;
import lombok.Data;

@Command
@Data
@AllArgsConstructor
public class AddTenantCommand {
    private String tenantName;
}
