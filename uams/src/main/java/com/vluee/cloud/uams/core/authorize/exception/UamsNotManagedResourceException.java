package com.vluee.cloud.uams.core.authorize.exception;

/**
 * 对应的资源未被管理， 比如某个REST API还未曾被授权过
 */
public class UamsNotManagedResourceException extends RuntimeException {
    public UamsNotManagedResourceException(String message) {
        super(message);
    }

    public UamsNotManagedResourceException() {
        super("该资源未被管理，拒绝访问");
    }
}
