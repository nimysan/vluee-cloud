package com.vluee.cloud.uams.interfaces.write.timer;

import com.vluee.cloud.commons.cqrs.command.Gate;
import com.vluee.cloud.uams.application.command.AddApiCommand;
import com.vluee.cloud.uams.core.permission.domain.RestApiResourcesProvider;
import com.vluee.cloud.uams.core.resources.domain.RestApi;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RefreshManagedApis {

    private final RestApiResourcesProvider restApiResourcesProvider;
    private final Gate commandGate;

    /**
     * 获取所有gateway管理的API并提交给UAMS做权限管理
     */
    @PostMapping("/refresh/apis")
    public void refreshApis() {
        //http://localhost:8080/swagger-resources
        //http://localhost:8080/saas-uams/v2/api-docs
        List<RestApi> apis = restApiResourcesProvider.apis();
        apis.stream().forEach(t -> {
            AddApiCommand command = AddApiCommand.builder().verb(t.getVerb()).url(t.getUrl()).build();
            commandGate.dispatch(command);
        });
    }
}
