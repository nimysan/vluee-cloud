package com.vluee.cloud.auth.interfaces.rest;

import com.vluee.cloud.auth.core.client.domain.OauthClientDetails;
import com.vluee.cloud.auth.core.client.service.ClientManageService;
import com.vluee.cloud.auth.interfaces.rest.vo.ClientDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientManageService clientService;

    @PostMapping
    public void registerClient(@RequestParam String clientId, @RequestParam String clientName) {
        clientService.registerClient(clientId, clientName);
    }


    @GetMapping
    public List<ClientDetailVo> listClients() {
        List<OauthClientDetails> oauthClientDetails = clientService.listClients();
        return oauthClientDetails.stream().map(this::assemble).collect(Collectors.toList());
    }

    private ClientDetailVo assemble(OauthClientDetails ocd) {
        return ClientDetailVo.builder().clientId(ocd.getClientId()).clientName(ocd.getClientName()).scope(ocd.getScope()).authorizedGrantTypes(ocd.getAuthorizedGrantTypes()).build();
    }

    @PostMapping("/{clientId}/resetPassword")
    public void resetPassword(@PathVariable String clientId) {
        clientService.resetPassword(clientId);
    }
}
