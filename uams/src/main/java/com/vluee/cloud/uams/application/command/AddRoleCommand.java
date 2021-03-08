package com.vluee.cloud.uams.application.command;

import com.vluee.cloud.commons.cqrs.annotations.Command;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Command
public class AddRoleCommand implements Serializable {
    public String roleName;
}
