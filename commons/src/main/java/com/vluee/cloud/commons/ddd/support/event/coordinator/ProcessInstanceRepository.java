package com.vluee.cloud.commons.ddd.support.event.coordinator;

import java.util.Collection;

public interface ProcessInstanceRepository {

    public void save(ProcessInstance processInstance);

    public Collection<ProcessInstance> getAllInstances();
}
