package com.vluee.cloud.uams.interfaces.command.rest;

import com.vluee.cloud.uams.application.UamsApplicationService;
import com.vluee.cloud.uams.core.resource.service.RestResourceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GrantController {

    private final RestResourceService restResourceService;

    private final UamsApplicationService applicationService;

    @PostMapping("/grant/resources/{resourceId}/roles/{roleId}")
    public void grantRestResource(@PathVariable Long resourceId, @PathVariable Long roleId) {
        applicationService.grant(resourceId, roleId);
    }

    @PostMapping("/resources/rest")
    public void registerRestResource(@RequestParam String verb, @RequestParam String urlPattern, @RequestParam(required = false) String name) {
        restResourceService.createResource(verb, urlPattern, name);
    }

}
