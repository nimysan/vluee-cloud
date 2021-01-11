package com.vluee.cloud.uams.interfaces.rest;

import com.vluee.cloud.uams.core.uams.service.UamsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("grants")
public class GrantController {

    @Autowired
    private UamsService uamsService;

    @ApiOperation("给用户分配角色")
    @PutMapping("roles/{roleId}/users/{userId}")
    public void grantUserRole(@ApiParam(value = "roleId", required = true) @PathVariable String roleId, @ApiParam(value = "userId", required = true) @PathVariable String userId) {
        uamsService.assignRoleToUser(userId, roleId);
    }

    @ApiOperation("给角色分配资源访问权限")
    @PostMapping("roles/{roleId}/resources/{resourceId}")
    public void grantRolePermissions(
            @ApiParam(value = "roleId", required = true) @PathVariable String roleId,
            @ApiParam(value = "resourceId", required = true) @PathVariable String resourceId
    ) {
        uamsService.assignResourceToRole(resourceId, roleId);
    }

    @PutMapping("roles/{roleId}/groups/{groupId}")
    public void grantGroupRole(@PathVariable String roleId, @PathVariable String groupId) {
        uamsService.assignRoleToGroup(groupId, roleId);
    }

    @GetMapping("checking")
    public boolean testGrant(@RequestParam String userId, @RequestParam String resourceId) {
        return uamsService.checkGrant(userId, resourceId);
    }
}
