package com.vluee.cloud.commons.distributedlock;

import cn.hutool.core.net.NetUtil;
import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 基于数据库实现的分布式锁
 */
@AllArgsConstructor
@Slf4j
public class MutexLockFactory {

    private final MutexLockRepository mutexLockRepository;
    private final LongIdGenerator longIdGenerator;

    public synchronized MutexLock lock(String resource, TimeUnit timeUnit, long duration) throws MutexLockLockException {
        long maxWaiting = timeUnit.toNanos(duration);

        if (maxWaiting > TimeUnit.SECONDS.toNanos(5)) {
            throw new MutexLockLockException("The timeout can't be more that 5");
        }
        //自旋等待
        final long at = System.nanoTime();
        for (; ; ) { //spin
            try {
                //lock lock lock until forever
                Optional<MutexLock> lockResource = mutexLockRepository.findResourceLock(resource);
                if (lockResource.isPresent()) {
                    final MutexLock theMutexLock = lockResource.get();
                    if (theMutexLock.guessDeadLock()) {
                        mutexLockRepository.delete(theMutexLock);//假设真有操作执行了超过30s， 则有可能带来意外的风险
                        return newLockResource(resource);
                    }
                } else {
                    return newLockResource(resource);
                }
            } catch (Throwable e) {
                log.error("Failed to lock " + resource, e);
                throw new MutexLockLockException("Can't lock due to " + e.getMessage());
            } finally {
                pause(TimeUnit.MILLISECONDS, 100); //每次等待100ms
            }

            if ((System.nanoTime() - at) >= maxWaiting) {
                if (log.isDebugEnabled()) {
                    log.debug("Wait timeunit {} and duration {} and max waiting {}", timeUnit, duration, maxWaiting);
                }
                throw new MutexLockLockException("Lock timeout(Can't lock)"); //指定时间内获取锁异常
            }
        }


        //查看是否已经被锁s
    }

    private void pause(TimeUnit timeUnit, long duration) {
        try {
            Thread.sleep(timeUnit.toMillis(duration));
        } catch (InterruptedException e) {
        }
    }

    /**
     * 只有获取到锁，才能操作
     *
     * @param resource            资源标识符
     * @param timeUnit            等待时间单位
     * @param timeout             等待时长
     * @param operationWithinLock 获取锁后的操作
     */
    public synchronized void workWithLock(String resource, TimeUnit timeUnit, long timeout, OperationWithinLock operationWithinLock) throws MutexLockLockException {
        MutexLock mutexLock = null;
        try {
            mutexLock = lock(resource, timeUnit, timeout);
            if (log.isDebugEnabled()) {
                log.debug("Success get lock {}", mutexLock);
            }
            operationWithinLock.execute();//business logic
        } catch (MutexLockLockException lockException) {
            throw lockException;
        } catch (Throwable e) {
            log.info("error {}", e.getMessage());
            throw new MutexLockLockException("Fail to lock", e);
        } finally {
            if (mutexLock != null) {
                mutexLock.unlock();
                mutexLockRepository.save(mutexLock);
            }
        }
    }

    private MutexLock newLockResource(String resource) {
        MutexLock mutexLock = new MutexLock(longIdGenerator.nextId(), resource, NetUtil.getLocalhostStr() + " - " + Thread.currentThread().getName());
        mutexLockRepository.save(mutexLock);
        return mutexLock;
    }

}
