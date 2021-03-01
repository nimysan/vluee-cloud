package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.core.usergroup.domain.UserGroup;
import com.vluee.cloud.uams.core.usergroup.domain.UserGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@ApplicationService
@Service
public class UserApplicationService {

    private final UserGroupRepository userGroupRepository;

    public void createUserGroup(String groupName) {
        UserGroup userGroup = new UserGroup(AggregateId.generate(), groupName);
        userGroupRepository.save(userGroup);
    }

    public void addUserToGroup(AggregateId userId, AggregateId groupId) {
        UserGroup userGroup = userGroupRepository.findById(groupId).orElseThrow(RuntimeException::new);
        userGroup.addUser(userId);
        userGroupRepository.save(userGroup);
    }
}
