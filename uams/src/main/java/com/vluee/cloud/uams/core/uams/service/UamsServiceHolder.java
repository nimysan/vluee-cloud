package com.vluee.cloud.uams.core.uams.service;

import com.vluee.cloud.commons.common.spring.SpringContextUtils;
import org.springframework.stereotype.Component;

/**
 * 静态类， 用于获取UamsService
 */
@Component
public final class UamsServiceHolder extends SpringContextUtils {

    public static UamsService uamsService() {
        return UamsServiceHolder.getContext().getBean(UamsService.class);
    }
}
