package com.vluee.cloud.statistics.application.commands;

import com.vluee.cloud.commons.cqrs.annotations.Command;
import lombok.AllArgsConstructor;
import lombok.Data;

@Command
@Data
@AllArgsConstructor
public class AddTenantCommand {
    private String tenantName;
}
