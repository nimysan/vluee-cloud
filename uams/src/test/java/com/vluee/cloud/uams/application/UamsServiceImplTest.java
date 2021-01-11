package com.vluee.cloud.uams.application;

import com.vluee.cloud.commons.common.AiStoreConstants;
import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.brand.exception.BrandNotExistException;
import com.vluee.cloud.commons.common.uams.ResourceDescriptor;
import com.vluee.cloud.commons.common.uams.RestResourceDescriptor;
import com.vluee.cloud.uams.core.brand.service.Brand;
import com.vluee.cloud.uams.core.brand.service.BrandQueryService;
import com.vluee.cloud.uams.core.uams.domain.UamsProtectedResource;
import com.vluee.cloud.uams.core.uams.domain.UamsRole;
import com.vluee.cloud.uams.core.uams.domain.UamsUser;
import com.vluee.cloud.uams.core.uams.domain.UamsUserGroup;
import com.vluee.cloud.uams.core.uams.exception.UamsInvalidOperationException;
import com.vluee.cloud.uams.core.uams.service.UamsService;
import com.vluee.cloud.uams.core.uams.service.UamsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
@Tag("授权测试")
class UamsServiceImplTest {

    @Autowired
    private UamsService uamsService;

    private final String tenantId = "mock-tenant";

    private final String userCode = "test_user";

    @MockBean
    private BrandQueryService saasBrandQueryService;

    @Inject
    private AutowireCapableBeanFactory spring;


    @BeforeEach
    public void initialize() {
        uamsService = new UamsServiceImpl(new AuditContext() {
            @Override
            public String getOpId() {
                return "gateway";
            }
        }, saasBrandQueryService);

        spring.autowireBean(uamsService);
    }

    @Test
    @Tag("user")
    @DisplayName("测试-用户可以归属给品牌")
    public void testRoleAssignToUserWithBrand() {
        String tenantId = "jj";
        Brand brand1 = new Brand(tenantId, "锦江", "b1", "维也纳");
        Brand brand2 = new Brand(tenantId, "锦江", "b2", "维也纳三好");
        Mockito.when(saasBrandQueryService.getBrand(brand1.getBrandId())).then((Answer<Optional<Brand>>) invocation -> Optional.of(brand1));
        Mockito.when(saasBrandQueryService.getBrand(brand2.getBrandId())).then((Answer<Optional<Brand>>) invocation -> Optional.of(brand2));

        UamsUser testUser = uamsService.createUser(tenantId, "testUser");
        uamsService.assignUserToBrand(brand1.getBrandId(), testUser.getId());

        Iterable<String> brandByUser = uamsService.getBrandByUser(testUser.getId());
        Collection<String> brandIds = IterableUtil.toCollection(brandByUser);
        assertNotNull(brandIds);
        assertEquals(1, brandIds.size());
        assertTrue(brandIds.contains(brand1.getBrandId()));

        uamsService.assignUserToBrand(brand2.getBrandId(), testUser.getId());

        brandByUser = uamsService.getBrandByUser(testUser.getId());
        brandIds = IterableUtil.toCollection(brandByUser);
        assertNotNull(brandIds);
        assertEquals(2, brandIds.size());
        assertTrue(brandIds.contains(brand2.getBrandId()));
        assertTrue(brandIds.contains(brand1.getBrandId()));
    }

    @Test
    @DisplayName("验证创建属于品牌的角色")
    public void testCreateRoleBelongToBrand() {
        assertThrows(BrandNotExistException.class, () -> uamsService.createRole("jj", "manager", "b1"));

        Mockito.when(saasBrandQueryService.getBrand("b1")).then((Answer<Optional<Brand>>) invocation -> Optional.of(new Brand("jj", "锦江", "b1", "维也纳")));
        assertNotNull(uamsService.createRole("jj", "manager", "b1"));

        Mockito.when(saasBrandQueryService.getBrand("b1")).then((Answer<Optional<Brand>>) invocation -> Optional.of(new Brand("dc", "锦江", "b2", "城市快捷")));
        assertThrows(IllegalArgumentException.class, () -> uamsService.createRole("dc", "manager", "b1"));
    }


    @Test
    @DisplayName("测试用户通过用户组获取角色")
    public void testUserHasRole() {
        String roleName = "test_role";
        UamsUser user = uamsService.createUser(tenantId, userCode);
        UamsUserGroup group = uamsService.createGroup(tenantId, "hello");
        uamsService.assignUserToGroup(user.getId(), group.getId());

        UamsRole role = uamsService.createRole(tenantId, roleName);
        uamsService.assignRoleToGroup(group.getId(), role.getId());

        //通过group获取roles
        assertTrue(uamsService.getRolesByUser(user.getId()).contains(role));
    }


    @Test
    @DisplayName("测试用户直接分配角色")
    @Transactional
    public void testUserHasRoleByDirectRoleMap() {
        String roleName = "test_role";
        UamsUser user = uamsService.createUser(tenantId, userCode);
        UamsRole role = uamsService.createRole(tenantId, roleName);
        uamsService.assignRoleToUser(user.getId(), role.getId());

        assertTrue(uamsService.getRolesByUser(user.getId()).contains(role));
    }


    @Test
    @DisplayName("测试用户同时分配直接角色和用户组角色")
    public void testUserGetRoleFromDirectRoleAndGroupRole() {
        String roleName = "test_role";
        String roleName2 = "test_role_2";
        UamsUser user = uamsService.createUser(tenantId, userCode);
        UamsUserGroup group = uamsService.createGroup(tenantId, "hello");
        uamsService.assignUserToGroup(user.getId(), group.getId());
        UamsRole role = uamsService.createRole(tenantId, roleName);
        uamsService.assignRoleToUser(user.getId(), role.getId());

        UamsRole role_group = uamsService.createRole(tenantId, roleName2);
        uamsService.assignRoleToGroup(group.getId(), role_group.getId());

        //通过group获取roles
        Collection<UamsRole> rolesByUser = uamsService.getRolesByUser(user.getId());
        assertTrue(rolesByUser.contains(role));
        assertTrue(rolesByUser.contains(role_group));
    }

    @Test
    @DisplayName("通过角色获取授权")
    public void testGetPermissions() {
        String roleName = "test_role";
        UamsRole role = uamsService.createRole(tenantId, roleName);
        RestResourceDescriptor resource = RestResourceDescriptor.builder().verb(HttpMethod.GET.name()).resource("/task/**").name("获取任务详情").build();
        UamsProtectedResource protectedResource = uamsService.createProtectedResource(resource, "gateway");
        uamsService.assignResourceToRole(protectedResource.getId(), role.getId());

        role = uamsService.loadRole(role.getId());
        assertEquals(1, role.getResources().size());

        UamsRole finalRole = uamsService.loadRole(role.getId());

        // 用户有用授权角色A， 角色A拥有authority code (task_create/task_delete), task_create关联 'POST /task' 资源。 该用户能够访问资源
        assertTrue(authorityDecision(finalRole, resource), "可以访问");

        RestResourceDescriptor taskCreateResource = RestResourceDescriptor.builder().verb(HttpMethod.POST.name()).resource("/task").name("创建任务").build();
        assertFalse(authorityDecision(finalRole, taskCreateResource), "未分配权限，无法访问");

    }

    /**
     * 模拟Spring Security Grant方法，测试是否能够正常访问授权资源
     *
     * @return decision
     */
    private boolean authorityDecision(UamsRole role, ResourceDescriptor resourceDescriptor) {
        Collection<String> authorityCodes = role.getAllResourceKeys(AiStoreConstants.UamsPermissionType.API);
        if (authorityCodes == null) {
            throw new RuntimeException("无法正常获取Role关联的授权码");
        }
        //读取所有的授权配置
        Map<String, ResourceDescriptor> allResources = uamsService.listResources();
        for (String code : allResources.keySet()) {
            boolean thisResource = allResources.get(code).equals(resourceDescriptor);
            if (thisResource) {
                if (authorityCodes.contains(code)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    @DisplayName("测试-不允许将非品牌归属的ROLE分配给品牌归属的员工")
    public void testRoleFilterByBrand() {

        Brand brand1 = new Brand(tenantId, "锦江", "b1", "维也纳");
        Brand brand2 = new Brand(tenantId, "锦江", "b2", "维也纳三好");
        Mockito.when(saasBrandQueryService.getBrand(brand1.getBrandId())).then((Answer<Optional<Brand>>) invocation -> Optional.of(brand1));
        Mockito.when(saasBrandQueryService.getBrand(brand2.getBrandId())).then((Answer<Optional<Brand>>) invocation -> Optional.of(brand2));

        String tenantId = "jj";
        UamsRole role = uamsService.createRole(tenantId, "manager");
        UamsRole brandRole = uamsService.createRole(tenantId, "manager", brand1.getBrandId());


        UamsUser user = uamsService.createUser(tenantId, "test_user");
        uamsService.assignUserToBrand(brand2.getBrandId(), user.getId());

        assertFalse(user.isOwnAllBrands(), "用户不是所有品牌管理者");
        assertThrows(UamsInvalidOperationException.class, () -> uamsService.assignRoleToUser(user.getId(), brandRole.getId()), "不允许分配特定品牌的角色给非特定品牌用户");
    }

    @Test
    @DisplayName("测试-测试品牌特定角色允许分配给拥有所有品牌的用用户")
    public void testRoleAssignToRuleWithOwnAllBrands() {

        Brand brand1 = new Brand(tenantId, "锦江", "b1", "维也纳");
        Brand brand2 = new Brand(tenantId, "锦江", "b2", "维也纳三好");
        Mockito.when(saasBrandQueryService.getBrand(brand1.getBrandId())).then((Answer<Optional<Brand>>) invocation -> Optional.of(brand1));
        Mockito.when(saasBrandQueryService.getBrand(brand2.getBrandId())).then((Answer<Optional<Brand>>) invocation -> Optional.of(brand2));

        String tenantId = "jj";
        UamsRole role = uamsService.createRole(tenantId, "manager");
        UamsRole brandRole = uamsService.createRole(tenantId, "manager", brand1.getBrandId());


        UamsUser user = uamsService.createUser(tenantId, "test_user");

        uamsService.setOwnAllBrands(user.getId());
        uamsService.assignRoleToUser(user.getId(), brandRole.getId());
        assertTrue(uamsService.getRolesByUser(user.getId()).contains(brandRole));
    }

    @Test
    @DisplayName("测试-品牌特定用户组只可以关联给品牌特定用户")
    @Transactional
    public void testUserAndGroup() {
        //品牌特定用户组只可以关联给品牌特定用户
        Brand brand1 = new Brand(tenantId, "锦江", "b1", "维也纳");
        Brand brand2 = new Brand(tenantId, "锦江", "b2", "维也纳三好");
        Mockito.when(saasBrandQueryService.getBrand(brand1.getBrandId())).thenReturn(Optional.of(brand1));
        Mockito.when(saasBrandQueryService.getBrand(brand2.getBrandId())).thenReturn(Optional.of(brand2));

        String tenantId = "jj";
        UamsUserGroup group = uamsService.createGroup(tenantId, "店总", brand1.getBrandId());
        UamsUser user = uamsService.createUser(tenantId, "user");

        //不允许关联
        assertThrows(UamsInvalidOperationException.class, () -> uamsService.assignUserToGroup(user.getId(), group.getId()));

        uamsService.assignUserToBrand(brand1.getBrandId(), user.getId());
        uamsService.assignUserToGroup(user.getId(), group.getId());

        assertTrue(uamsService.getUserGroupByUser(user.getId()).contains(group));
    }

    @Test
    @DisplayName("测试-非品牌特定用户组可以关联给任何用户")
    @Transactional
    public void testUserAndGroupCasIe2() {
        //品牌特定用户组只可以关联给品牌特定用户
        Brand brand1 = new Brand(tenantId, "锦江", "b1", "维也纳");
        Brand brand2 = new Brand(tenantId, "锦江", "b2", "维也纳三好");
        Mockito.when(saasBrandQueryService.getBrand(brand1.getBrandId())).thenReturn(Optional.of(brand1));
        Mockito.when(saasBrandQueryService.getBrand(brand2.getBrandId())).thenReturn(Optional.of(brand2));

        String tenantId = "jj";
        UamsUserGroup group = uamsService.createGroup(tenantId, "店总");
        UamsUser user = uamsService.createUser(tenantId, "user");

        uamsService.assignUserToGroup(user.getId(), group.getId());
        assertTrue(uamsService.getUserGroupByUser(user.getId()).contains(group));

        UamsUser user2 = uamsService.createUser(tenantId, "user");
        uamsService.assignUserToBrand(brand2.getBrandId(), user2.getId());
        uamsService.assignUserToGroup(user2.getId(), group.getId());

        assertTrue(uamsService.getUserGroupByUser(user2.getId()).contains(group));
    }

    @Test
    @DisplayName("测试-品牌特定用户组可以关联给 \"超级品牌\" 用户")
    @Transactional
    public void testUserAndGroupCase3() {
        //品牌特定用户组只可以关联给品牌特定用户
        Brand brand1 = new Brand(tenantId, "锦江", "b1", "维也纳");
        Brand brand2 = new Brand(tenantId, "锦江", "b2", "维也纳三好");
        Mockito.when(saasBrandQueryService.getBrand(brand1.getBrandId())).thenReturn(Optional.of(brand1));
        Mockito.when(saasBrandQueryService.getBrand(brand2.getBrandId())).thenReturn(Optional.of(brand2));

        String tenantId = "jj";
        UamsUserGroup group = uamsService.createGroup(tenantId, "店总", brand1.getBrandId());
        UamsUser user = uamsService.createUser(tenantId, "user");

        assertThrows(UamsInvalidOperationException.class, () -> uamsService.assignUserToGroup(user.getId(), group.getId()));

        uamsService.setOwnAllBrands(user.getId());
        uamsService.assignUserToGroup(user.getId(), group.getId());
        assertTrue(uamsService.getUserGroupByUser(user.getId()).contains(group));
    }


    //    @TestConfiguration
    public static class ForMe {

        @Bean
        public AuditContext auditContext() {
            return () -> "test-context";
        }
    }

}