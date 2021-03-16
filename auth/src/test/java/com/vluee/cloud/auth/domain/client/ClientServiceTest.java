package com.vluee.cloud.auth.domain.client;

import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.ddd.exception.EntityAlreadyExistException;
import com.vluee.cloud.auth.core.client.service.ClientManageService;
import com.vluee.cloud.auth.core.client.domain.OauthClientDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientManageService clientService;

    @Test
    public void testRegisterClient() {
        OauthClientDetails client = clientService.registerClient("test1", "gateway");
        assertNotNull(client);
        assertEquals("test1", client.getClientId());

        assertThrows(EntityAlreadyExistException.class, () -> clientService.registerClient("test1", "sec"));
    }

    @TestConfiguration
    public static class Config {
        @Bean
        public AuditContext test() {
            return new AuditContext() {
                @Override
                public String getOpId() {
                    return "123";
                }
            };
        }
    }
}