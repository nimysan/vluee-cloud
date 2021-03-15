package com.vluee.cloud.uams.application.command.handler;

import com.vluee.cloud.commons.cqrs.annotations.CommandHandlerAnnotation;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.AddApiCommand;
import com.vluee.cloud.uams.core.resources.domain.ApiResource;
import com.vluee.cloud.uams.core.resources.domain.ApiResourceRepository;
import com.vluee.cloud.uams.core.resources.domain.ResourceFactory;
import com.vluee.cloud.uams.core.resources.domain.RestApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    /**
     * 如果方法和url都不一致， 则添加， 否则忽略
     *
     * @param registerApiCommand
     * @return
     */
    public Void handle(AddApiCommand registerApiCommand) {
        List<ApiResource> apiResources = apiResourceRepository.loadByUrl(registerApiCommand.getUrl());
        Optional<ApiResource> any = apiResources.stream().filter(t -> t.getRestApi().getVerb().equalsIgnoreCase(registerApiCommand.getVerbKey())).findAny();
        if (!any.isPresent()) {//如果存在相同方法和url的api, 则忽略， 否则添加进入管理平台
            apiResourceRepository.save(resourceFactory.createApiResource(new RestApi(registerApiCommand.getVerbKey(), registerApiCommand.getUrl()), null));
        }
        return null;
    }
}
