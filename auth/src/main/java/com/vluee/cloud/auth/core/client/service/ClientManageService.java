package com.vluee.cloud.auth.core.client.service;

import com.vluee.cloud.auth.core.client.domain.OauthClientDetails;
import com.vluee.cloud.auth.core.client.domain.OauthClientDetailsRepository;
import com.vluee.cloud.auth.core.client.exception.ClientNotExistException;
import com.vluee.cloud.auth.spring.security.tokengranter.ExtGrantType;
import com.vluee.cloud.commons.common.audit.AuditContext;
import com.vluee.cloud.commons.common.ddd.exception.EntityAlreadyExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ClientManageService {

    private final OauthClientDetailsRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuditContext auditContext;

    /**
     * 注册一个client
     *
     * @param clientId
     * @param clientName
     */
    @Transactional
    public OauthClientDetails registerClient(@NotBlank String clientId, @NotBlank String clientName) {
        Optional<OauthClientDetails> alreadyExistClient = clientRepository.findById(clientId);
        if (alreadyExistClient.isPresent()) {
            throw new EntityAlreadyExistException();
        }
        OauthClientDetails client = OauthClientDetails.builder().clientId(clientId).clientName(clientName).clientSecret(commenceSecret()).scope("all")
                .authorizedGrantTypes("password,refresh_token,"+ ExtGrantType.verification_code.name()).accessTokenValidity(3600 * 24).refreshTokenValidity(3600 * 24 * 7).build();
        auditContext.audit(client);
        clientRepository.save(client);
        return client;
    }

    private String commenceSecret() {
        return passwordEncoder.encode(randomSecret());
    }

    private String randomSecret() {
        String secret = RandomStringUtils.randomAlphanumeric(12);
        log.info("-----------######## {} ########--------------", secret);
        return "8NPgEEuhuve1";//for testing secret;
    }

    @Transactional
    public void resetPassword(String clientId) {
        OauthClientDetails oauthClientDetails = clientRepository.findById(clientId).orElseThrow(ClientNotExistException::new);
        oauthClientDetails.setClientSecret(commenceSecret());
        auditContext.auditModify(oauthClientDetails);
        clientRepository.save(oauthClientDetails);
    }

    public List<OauthClientDetails> listClients() {
        return clientRepository.findAll();
    }
}
