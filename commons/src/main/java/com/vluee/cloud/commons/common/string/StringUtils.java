package com.vluee.cloud.commons.common.string;

public final class StringUtils {

    public static boolean isNotEmpty(String target) {
        return org.apache.commons.lang.StringUtils.isNotBlank(target);
    }
}
