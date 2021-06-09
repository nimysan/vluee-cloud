package com.vluee.cloud.tenants.interfaces.query.rest;

import com.vluee.cloud.tenants.core.search.SearchCondition;
import com.vluee.cloud.tenants.core.search.SearchResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api
@org.springframework.web.bind.annotation.RestController
public class SearchController {

    @ApiOperation("通用搜索接口")
    @GetMapping("/search")
    public SearchResult search(@RequestBody SearchCondition searchCondition) {
        return null;
    }
}
