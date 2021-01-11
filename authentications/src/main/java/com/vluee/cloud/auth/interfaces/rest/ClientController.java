package com.vluee.cloud.auth.interfaces.rest;

import com.vluee.cloud.auth.core.client.service.ClientManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientManageService clientService;

    @PostMapping
    public void registerClient(@RequestParam String clientId, @RequestParam String clientName) {
        clientService.registerClient(clientId, clientName);
    }

    @PostMapping("/{clientId}/resetPassword")
    public void resetPassword(@PathVariable String clientId) {
        clientService.resetPassword(clientId);
    }
}
