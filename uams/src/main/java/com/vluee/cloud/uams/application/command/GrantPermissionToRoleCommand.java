package com.vluee.cloud.uams.application.command;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class GrantPermissionToRoleCommand {
    private AggregateId roleId;
    private AggregateId permissionId;
}
