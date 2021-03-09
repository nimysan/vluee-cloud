package com.vluee.cloud.uams.application.listeners;

import com.vluee.cloud.commons.ddd.annotations.event.EventListeners;
import lombok.AllArgsConstructor;

@EventListeners
@AllArgsConstructor
public class ApiPermissionRedisCacheListener {


    public void refresh() {
    }

    //for testing
    public boolean checkApiPermission(String username, String verb, String urlPattern) {
        //从缓存中读取状态
        return false;
    }
}
