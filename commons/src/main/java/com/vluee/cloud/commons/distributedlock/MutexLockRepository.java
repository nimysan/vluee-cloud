package com.vluee.cloud.commons.distributedlock;

import com.vluee.cloud.commons.distributedlock.MutexLock;

import java.sql.SQLException;
import java.util.Optional;

public interface MutexLockRepository {

    public Optional<MutexLock> findResourceLock(String resource) throws SQLException;

    public void delete(MutexLock mutexLock);

    void save(MutexLock mutexLock);
}
