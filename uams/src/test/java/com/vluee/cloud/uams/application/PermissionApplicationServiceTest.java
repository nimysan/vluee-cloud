package com.vluee.cloud.uams.application;

import com.vluee.cloud.uams.core.permission.Permission;
import com.vluee.cloud.uams.core.permission.PermissionRepository;
import com.vluee.cloud.uams.core.permission.RestApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Slf4j
class PermissionApplicationServiceTest {

    private PermissionApplicationService applicationService;
    private PermissionRepository permissionRepository;

    @BeforeEach
    public void setup() {
        this.permissionRepository = Mockito.mock(PermissionRepository.class);
        this.applicationService = new PermissionApplicationService(permissionRepository);
    }

    @Test
    public void testRegisterRestApiResource() {
        //REST API 由 http method和url共同决定 样例： POST /hotels/{hotelId}/name?name=newHotelName
        String resourceName = "酒店名称";
        String description = "酒店名称";
        String restUrl = "/hotels/{hotelId}/name";

        RestApi restApi = new RestApi("post", restUrl);
        Permission permission = applicationService.registerApiPermission(restApi, resourceName, description);

        //确保调用了存储操作
        Mockito.verify(permissionRepository).save(permission);

        Assertions.assertNotNull(permission);
        Assertions.assertEquals(resourceName, permission.getResource().getName());
        Assertions.assertEquals("post", permission.getOperation().getVerb());
        log.info(permission.toString());
    }

}