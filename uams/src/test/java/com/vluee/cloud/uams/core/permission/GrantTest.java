package com.vluee.cloud.uams.core.permission;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.uams.core.role.domain.CRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GrantTest {

    private PermissionFactory permissionFactory = new PermissionFactory();

    @Test
    public void testGrant() {

        Permission apiPermission = permissionFactory.createApiPermission("GET", "/hotels", "酒店", "获取酒店列表");
        AggregateId roleId = AggregateId.generate();
        CRole role = new CRole(roleId, "super");

        //授权
        AggregateId grantId = AggregateId.generate();
        Grant grant = new Grant(grantId, role.getAggregateId(), apiPermission.getAggregateId());

        boolean grantOperation = grant.positiveMatch(role.getAggregateId(), apiPermission.getAggregateId());
        Assertions.assertTrue(grantOperation);

        //取消授权
        grant.cancelGrant();
        grantOperation = grant.positiveMatch(role.getAggregateId(), apiPermission.getAggregateId());
        Assertions.assertFalse(grantOperation);

        //再次授权
        AggregateId grantNewId = AggregateId.generate();
        grant = new Grant(grantId, role.getAggregateId(), apiPermission.getAggregateId());

        grantOperation = grant.positiveMatch(role.getAggregateId(), apiPermission.getAggregateId());
        Assertions.assertTrue(grantOperation);
    }

}