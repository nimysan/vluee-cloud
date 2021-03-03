package com.vluee.cloud.uams.application.command;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AddRoleCommand implements Serializable {
    public String roleName;
}
