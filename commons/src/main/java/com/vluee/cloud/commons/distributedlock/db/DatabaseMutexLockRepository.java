package com.vluee.cloud.commons.distributedlock.db;

import com.vluee.cloud.commons.distributedlock.MutexLock;
import com.vluee.cloud.commons.distributedlock.MutexLockRepository;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DatabaseMutexLockRepository implements MutexLockRepository {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Optional<MutexLock> findResourceLock(String resource) {
        EntityTransaction transaction = entityManager.getTransaction();
        MutexLock mutexLock = null;
        try {
            transaction.begin();
            Query query = entityManager.createNativeQuery("select * from mutex_locks where resource_id='" + resource + "'", Tuple.class);
            List<Tuple> resultList = query.getResultList();
            if (resultList.size() == 1) {
                mutexLock = assemble(resultList.get(0));
            }
            transaction.commit();
        } catch (Throwable e) {
            transaction.rollback();
        }
        return Optional.ofNullable(mutexLock);
    }

    //TODO
    private MutexLock assemble(Tuple tuple) {
        log.info("Tuple {}", tuple);
        return new MutexLock();
    }

    @Override
    public void delete(MutexLock mutexLock) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query query = entityManager.createNativeQuery("delete from mutex_locks where lock_id=" + mutexLock.getId());
            query.executeUpdate();
            transaction.commit();
        } catch (Throwable e) {
            transaction.rollback();
        }
    }

    @Override
    public void save(MutexLock mutexLock) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query query = entityManager.createNativeQuery("");
            query.executeUpdate();
            transaction.commit();
        } catch (Throwable e) {
            transaction.rollback();
        }
    }
}
