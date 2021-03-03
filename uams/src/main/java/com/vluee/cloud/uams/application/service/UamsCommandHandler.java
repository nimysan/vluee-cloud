package com.vluee.cloud.uams.application.service;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.annotations.CommandHandlerAnnotation;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.uams.application.command.AddRoleCommand;
import com.vluee.cloud.uams.core.role.domain.CRole;
import com.vluee.cloud.uams.core.role.domain.CRoleRepository;
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
public class UamsCommandHandler implements CommandHandler<AddRoleCommand, Void> {

    private final CRoleRepository cRoleRepository;

    public Void handle(AddRoleCommand addRoleCommand) {
        CRole cRole = new CRole(AggregateId.generate(), addRoleCommand.getRoleName());
        cRoleRepository.save(cRole);
        return null;
    }
}
