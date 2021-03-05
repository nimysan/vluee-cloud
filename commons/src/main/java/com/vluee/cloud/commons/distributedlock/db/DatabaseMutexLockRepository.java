package com.vluee.cloud.commons.distributedlock.db;

import com.vluee.cloud.commons.distributedlock.MutexLock;
import com.vluee.cloud.commons.distributedlock.MutexLockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DatabaseMutexLockRepository implements MutexLockRepository, ApplicationContextAware {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ApplicationContext context;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public Optional<MutexLock> findResourceLock(String resource) throws SQLException {
        Query nativeQuery = entityManager.createNativeQuery("select * from mutex_locks where resource_identifier='" + resource + "' ", Tuple.class);
        List resultList = nativeQuery.getResultList();
        if (resultList.size() == 1) {
            MutexLock assemble = assemble((Tuple) resultList.get(0));
            if (assemble != null && assemble.isRemoved()) {
                delete(assemble);//删除旧资源
            } else {
                return Optional.ofNullable(assemble);
            }
        }
        return Optional.empty();
    }

    //TODO
    private MutexLock assemble(Tuple tuple) {
        log.info("Tuple {}", tuple);
//        @NotNull Long id, @NotNull String resourceIdentifier, String lockOwner
        MutexLock mutexLock = new MutexLock(tuple.get("id", BigInteger.class).longValue(), tuple.get("resource_identifier", String.class), tuple.get("lock_owner", String.class));
        mutexLock.setLockedTime(tuple.get("locked_at", Date.class));
        Boolean isRemoved = tuple.get("removed", Boolean.class);
        if (isRemoved) {
            mutexLock.unlock();
        }
        return mutexLock;
    }

    @Transactional
    @Override
    public void delete(MutexLock mutexLock) {
        Query nativeQuery = entityManager.createNativeQuery("delete from mutex_locks where id='" + mutexLock.getId() + "'");
        nativeQuery.executeUpdate();
    }

    @Transactional
    @Override
    public void save(MutexLock mutexLock) {
        if (mutexLock.isRemoved()) {
            delete(mutexLock);
            return;
        }
        Query nativeQuery = entityManager.createNativeQuery("select count(id) from mutex_locks where id='" + mutexLock.getId() + "'");
        BigInteger singleResult = (BigInteger) nativeQuery.getSingleResult();
        if (singleResult.intValue() == 1) {
            String sql = String.format("update mutex_locks set locked_at='%s', removed='%s' where id=%s", dateStr(mutexLock.getLockedTime()), mutexLock.isRemoved() ? "1" : "0", mutexLock.getId());
            Query updateQuery = entityManager.createNativeQuery(sql);
            updateQuery.executeUpdate();
        } else {
            String format = String.format("('%s','%s','%s','%s','%s')", mutexLock.getId(), mutexLock.getResourceIdentifier(), dateStr(mutexLock.getLockedTime()), mutexLock.getLockOwner(), mutexLock.isRemoved() ? "1" : "0");
            Query insertQuery = entityManager.createNativeQuery("insert into mutex_locks (id,resource_identifier,locked_at,lock_owner,removed) values " + format);
            insertQuery.executeUpdate();
        }
    }

    public synchronized String dateStr(Date date) {
        //2021-03-05 13:29:38
        return SIMPLE_DATE_FORMAT.format(date);
    }

    private boolean exist(Object singleResult) {
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
