package com.vluee.cloud.commons.distributedlock;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * table_name: mutex_locks
 */
@NoArgsConstructor
@ToString
public class MutexLock {

    public MutexLock(@NotNull Long id, @NotNull String resourceIdentifier, String lockOwner) {
        this.id = id;
        this.resourceIdentifier = resourceIdentifier;
        this.lockOwner = lockOwner;
        this.lockedTime = DateUtil.date();
        this.removed = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutexLock mutexLock = (MutexLock) o;
        return Objects.equal(id, mutexLock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Getter
    private Long id;
    @Getter
    private String resourceIdentifier;

    @Getter
    private Date lockedTime;

    private boolean removed = false;

    @Getter
    private String lockOwner;

    /**
     * 如果一个lock是LOCKED且lock占用时间超过30s 则认为是死锁
     *
     * @return
     */
    public boolean guessDeadLock() {
        if (lockedTime == null) {
            return true;
        } else {
            long lockTime = DateUtil.date().getTime() - lockedTime.getTime();
            return lockTime >= 30 * 1000 || lockTime <= 0;
        }
    }

    /**
     * 释放锁
     */
    public void unlock() {
        this.removed = true;
    }

    public boolean isRemoved(){
        return this.removed;
    }

    enum LockStatus {
        LOCKED, UNLOCK
    }

}
