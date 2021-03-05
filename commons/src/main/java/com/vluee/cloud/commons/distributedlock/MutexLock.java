package com.vluee.cloud.commons.distributedlock;

import cn.hutool.core.date.DateUtil;
import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "lock_resources")
@NoArgsConstructor
@ToString
public class MutexLock extends BaseAggregateRoot {

    public MutexLock(@NotNull AggregateId aggregateId, @NotNull String resourceIdentifier, String lockOwner) {
        this.aggregateId = aggregateId;
        this.resourceIdentifier = resourceIdentifier;
        this.lockStatus = LockStatus.LOCKED;
        this.lockOwner = lockOwner;
        this.lockedTime = DateUtil.date();
    }

    @Getter
    @Column(nullable = false, unique = true)
    private String resourceIdentifier;

    @Enumerated
    private LockStatus lockStatus = LockStatus.AVAILABLE;

    @Getter
    @Column
    private Date lockedTime;

    @Column
    private String lockOwner;

    /**
     * 如果一个lock是LOCKED且lock占用时间超过30s 则认为是死锁
     *
     * @return
     */
    public boolean guessDeadLock() {
        if (LockStatus.LOCKED.equals(lockStatus)) {
            if (lockedTime == null) {
                return true;
            } else {
                long lockTime = DateUtil.date().getTime() - lockedTime.getTime();
                return lockTime >= 30 * 1000 || lockTime <= 0;
            }
        }
        return false;
    }

    /**
     * 释放锁
     */
    public void unlock() {
        //删除即解锁
        this.markAsRemoved();
    }

    enum LockStatus {
        LOCKED, AVAILABLE
    }

}
