package com.vluee.cloud.users.core.user.service;


import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.date.DateUtils;
import com.vluee.cloud.users.core.user.domain.LoginPassword;
import com.vluee.cloud.users.core.user.domain.LoginPasswordRepository;
import com.vluee.cloud.users.core.user.domain.User;
import com.vluee.cloud.users.core.user.domain.UserRepository;
import com.vluee.cloud.users.core.user.exception.UamsUserNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UamsServiceImpl implements UserService {

    private final UserRepository uamsUserRepository;

//    private final LoginPasswordRepository loginPasswordRepository;

    private final AuditContext auditContext;

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
    @Transactional
    public void changeNickName(String username, String nickName) {
        User user = uamsUserRepository.findByUserName(username).orElseThrow(UamsUserNotExistException::new);
        checkNickName(nickName);
        user.setNickName(nickName);
        auditModify(user);
        uamsUserRepository.save(user);
    }

    @Override
    @Transactional
    public void lockUser(String username) {
        User user = uamsUserRepository.findByUserName(username).orElseThrow(UamsUserNotExistException::new);
        user.setLocked(true);
        auditModify(user);
        uamsUserRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<LoginPassword> getLoginPassword(String username) {
//        return loginPasswordRepository.findByUsernameAndActiveStatus(username, true);
        return null;
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
