package com.vluee.cloud.uams.application.command;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UserJoinGroupCommand {
    private AggregateId userId;
    private AggregateId userGroupId;
}
