package com.vluee.cloud.uams;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.common.audit.BaseAuditConfig;
import com.vluee.cloud.commons.common.data.id.IdConfig;
import com.vluee.cloud.uams.core.permission.ApiPermission;
import com.vluee.cloud.uams.core.permission.PermissionFactory;
import com.vluee.cloud.uams.core.permission.PermissionRepository;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("cn.hutool.extra.spring"),
        @ComponentScan("com.vluee.cloud.uams")
})
@Import({IdConfig.class, BaseAuditConfig.class})
public class UamsApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(UamsApplication.class, args);
    }

    @Autowired
    private CRoleRepository cRoleRepository;

    @Autowired
    private PermissionFactory permissionFactory;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        List<AggregateId> aggregateIds = initializePermissions();
//        initRoles(aggregateIds.get(0), aggregateIds.get(1));
    }

    @Transactional
    public void initRoles(AggregateId p1, AggregateId p2) {
        CRole role = new CRole(AggregateId.generate(), "test1");
        cRoleRepository.save(role);

        role = new CRole(AggregateId.generate(), "super");
        cRoleRepository.save(role);

        role.addPermission(p1);
        role.addPermission(p2);
        cRoleRepository.save(role);
    }

    @Transactional
    public List<AggregateId> initializePermissions() {
        List<AggregateId> pids = new ArrayList<>(2);
        ApiPermission apiPermission = permissionFactory.createApiPermission("POST", "/hotels", "创建酒店", "新建一个酒店");
        permissionRepository.save(apiPermission);
        ApiPermission apiPermission2 = permissionFactory.createApiPermission("GET", "/hotels", "列出酒店", "新建一个酒店");
        permissionRepository.save(apiPermission2);

        pids.add(apiPermission.getAggregateId());
        pids.add(apiPermission2.getAggregateId());

        return pids;
    }
}
