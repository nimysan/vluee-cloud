package com.vluee.cloud.users.core.user.service;


import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.date.DateUtils;
import com.vluee.cloud.users.core.user.domain.User;
import com.vluee.cloud.users.core.user.domain.UserRepository;
import com.vluee.cloud.users.core.user.exception.UamsUserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
public class UamsServiceImpl implements UserService {

    @Autowired
    private UserRepository uamsUserRepository;

    private final AuditContext auditContext;

    public UamsServiceImpl(@NotNull AuditContext auditContext) {
        this.auditContext = auditContext;
    }

    @Override
    @Transactional
    public User createUser(String userCode) {
        User uu = new User(userCode);
        audit(uu);
        uamsUserRepository.save(uu);
        return uu;
    }

    @Override
    @Transactional
    public User loadUserByUserName(String username) {
        return uamsUserRepository.findByUserName(username).orElseThrow(UamsUserNotExistException::new);
    }

    @Override
    public void changeNickName(String username, String nickName) {
        User user = uamsUserRepository.findByUserName(username).orElseThrow(UamsUserNotExistException::new);
        checkNickName(nickName);
        user.setNickName(nickName);
        auditModify(user);
        uamsUserRepository.save(user);
    }

    private void checkNickName(String nickName) {

    }

    private void audit(AuditAware auditAware) {
        auditAware.setCreatedBy(auditContext.getOpId());
        auditAware.setCreatedAt(DateUtils.currentDate());
    }

    private void auditModify(AuditAware auditAware) {
        auditAware.setLastModifiedBy(auditContext.getOpId());
        auditAware.setLastModifiedAt(DateUtils.currentDate());
    }

}
