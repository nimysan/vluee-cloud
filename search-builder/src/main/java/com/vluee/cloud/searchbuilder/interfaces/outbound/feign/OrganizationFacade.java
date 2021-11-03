package com.vluee.cloud.searchbuilder.interfaces.outbound.feign;

import com.vluee.cloud.commons.common.ServiceConstants;
import com.vluee.cloud.commons.common.search.DocumentObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = ServiceConstants.Services.ORGINAZATION)
public interface OrganizationFacade {

    @RequestMapping("/hotels/{id}")
    public DocumentObject loadHotel(@PathVariable String id);

}
