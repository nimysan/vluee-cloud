package com.vluee.cloud.uams.core.user.domain;

import com.vluee.cloud.commons.ddd.annotations.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@ValueObject
@Embeddable
public class UserProfile {
    /**
     * 头像链接
     */
    @Column(length = 255)
    private String activator;
    /**
     * 自我介绍
     */
    @Column(length = 1024)
    private String selfDescription;
}
