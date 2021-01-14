package com.vluee.cloud.uams.core.resource.service;

import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.data.AuditAware;
import com.vluee.cloud.commons.common.date.DateUtils;
import com.vluee.cloud.uams.core.resource.domain.RestResource;
import com.vluee.cloud.uams.core.resource.domain.RestResourceRepository;
import com.vluee.cloud.uams.core.resource.exception.RestResourceNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RestResourceService {

    private final RestResourceRepository restResourceRepository;

    private final AuditContext auditContext;

    public RestResource createResource(String verb, String urlPattern, String name) {
        RestResource resource = new RestResource(verb, urlPattern, name);
        audit(resource);
        restResourceRepository.save(resource);
        return resource;
    }

    private void audit(AuditAware auditAware) {
        auditAware.setCreatedBy(auditContext.getOpId());
        auditAware.setCreatedAt(DateUtils.currentDate());
    }

    private void auditModify(AuditAware auditAware) {
        auditAware.setLastModifiedBy(auditContext.getOpId());
        auditAware.setLastModifiedAt(DateUtils.currentDate());
    }

    public RestResource loadResource(Long resourceId) {
        return restResourceRepository.findById(resourceId).orElseThrow(RestResourceNotExistException::new);
    }
}
