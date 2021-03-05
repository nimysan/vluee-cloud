package com.vluee.cloud.commons.distributedlock;

import com.vluee.cloud.commons.distributedlock.MutexLock;

import java.util.Optional;

public interface MutexLockRepository {

    public Optional<MutexLock> findResourceLock(String resource);

    public void delete(MutexLock mutexLock);

    void save(MutexLock mutexLock);
}
