package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.cqrs.annotations.CommandHandlerAnnotation;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.AddApiCommand;
import com.vluee.cloud.uams.core.resources.domain.ApiResourceRepository;
import com.vluee.cloud.uams.core.resources.domain.ResourceFactory;
import com.vluee.cloud.uams.core.resources.domain.RestApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * DDD application service。
 * DDD的应用层就是一些编排脚本，真正的业务逻辑请放回到domain相关代码去。
 */
@Service
@AllArgsConstructor
@ApplicationService
@CommandHandlerAnnotation
public class RegisterApiHandler implements CommandHandler<AddApiCommand, Void> {

    private final ApiResourceRepository apiResourceRepository;
    private final ResourceFactory resourceFactory;

    public Void handle(AddApiCommand registerApiCommand) {
        apiResourceRepository.save(resourceFactory.createApiResource(new RestApi(registerApiCommand.getVerb(), registerApiCommand.getUrl()), null));
        return null;
    }
}
