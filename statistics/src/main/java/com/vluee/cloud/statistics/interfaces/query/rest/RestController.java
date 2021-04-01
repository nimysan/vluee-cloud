package com.vluee.cloud.statistics.interfaces.query.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @ApiOperation("test接口说明")
    @GetMapping("/test")
    public String test(){
        return "1";
    }
}
