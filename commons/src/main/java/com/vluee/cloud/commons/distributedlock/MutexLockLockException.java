package com.vluee.cloud.commons.distributedlock;

/**
 * 获取锁异常
 */
public class MutexLockLockException extends Exception {
    public MutexLockLockException(String message) {
        super(message);
    }
}
