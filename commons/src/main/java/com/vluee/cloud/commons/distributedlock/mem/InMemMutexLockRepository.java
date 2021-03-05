package com.vluee.cloud.commons.distributedlock.mem;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.vluee.cloud.commons.distributedlock.MutexLock;
import com.vluee.cloud.commons.distributedlock.MutexLockRepository;

import java.util.Optional;
import java.util.Set;

public class InMemMutexLockRepository implements MutexLockRepository {

    private Set<MutexLock> resources = new ConcurrentHashSet<>();

    @Override
    public synchronized Optional<MutexLock> findResourceLock(String resource) {
        return resources.stream().filter(t -> !t.isRemoved()).filter(t -> t.getResourceIdentifier().equals(resource)).findFirst();
    }

    @Override
    public synchronized void delete(MutexLock mutexLock) {
        resources.remove(mutexLock);
    }

    @Override
    public synchronized void save(MutexLock mutexLock) {
        resources.add(mutexLock);
    }
}
