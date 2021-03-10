package com.vluee.cloud.uams.core.user.domain.events;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleGrantedEvent implements Serializable {
    private AggregateId userId;
    private AggregateId roleId;
}
