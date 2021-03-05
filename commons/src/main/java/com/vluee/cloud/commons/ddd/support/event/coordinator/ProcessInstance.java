package com.vluee.cloud.commons.ddd.support.event.coordinator;

import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;

public class ProcessInstance extends BaseAggregateRoot {

    /**
     * 最后运行时间与当前时间不超过5分钟
     *
     * @return
     */
    public boolean guessNotRunning() {
        return false;
    }

}
