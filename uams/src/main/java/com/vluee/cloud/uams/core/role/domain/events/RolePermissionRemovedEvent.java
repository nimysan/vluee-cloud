package com.vluee.cloud.uams.core.role.domain.events;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class RolePermissionRemovedEvent implements Serializable {

    private AggregateId roleId;

    private AggregateId permissionId;

}
