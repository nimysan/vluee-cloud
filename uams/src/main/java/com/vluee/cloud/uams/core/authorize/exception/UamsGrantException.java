package com.vluee.cloud.uams.core.authorize.exception;

public class UamsGrantException extends RuntimeException {

    private Long roleId;
    private Long permissionId;

    /**
     * @param roleId
     * @param permissionId
     * @param message
     */
    public UamsGrantException(Long roleId, Long permissionId, String message) {
        super(message);
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

}
