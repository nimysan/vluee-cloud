package com.vluee.cloud.uams.core.user.domain.events;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class UserAddedToGroupEvent implements Serializable {
    private AggregateId userId;
    private AggregateId userGroupId;
}
