package com.vluee.cloud.uams.interfaces.rest;

import com.vluee.cloud.commons.common.uams.RestResourceDescriptor;
import com.vluee.cloud.uams.core.uams.service.UamsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("apiresources")
@RestController
public class UamsResourceController {

    @Autowired
    private UamsService uamsService;

    @ApiOperation("创建API资源")
    @PostMapping
    public void createApiResource(
            @ApiParam(value = "resource", required = true) @RequestParam String resource,
            @ApiParam(value = "verb", required = true) @RequestParam String verb,
            @ApiParam(value = "name", required = true) @RequestParam String name,
            @ApiParam(value = "description", required = true) @RequestParam String description,
            @ApiParam(value = "contributor", required = true) @RequestParam String contributor
    ) {
        uamsService.createProtectedResource(RestResourceDescriptor.builder().resource(resource).verb(verb).name(name).description(description).build(), contributor);
    }


    @ApiOperation("获取所有API资源")
    @GetMapping
    public void listResources() {
        throw new UnsupportedOperationException("未实现该操作");
    }
}
