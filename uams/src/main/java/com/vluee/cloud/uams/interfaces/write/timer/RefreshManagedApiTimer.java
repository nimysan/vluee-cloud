package com.vluee.cloud.uams.interfaces.write.timer;

import com.vluee.cloud.commons.cqrs.command.Gate;
import com.vluee.cloud.commons.distributedlock.MutexLockFactory;
import com.vluee.cloud.commons.distributedlock.MutexLockLockException;
import com.vluee.cloud.commons.distributedlock.OperationWithinLock;
import com.vluee.cloud.uams.application.command.AddApiCommand;
import com.vluee.cloud.uams.application.service.RestApiResourcesProvider;
import com.vluee.cloud.uams.core.resources.domain.RestApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@Slf4j
public class RefreshManagedApiTimer {

    private final RestApiResourcesProvider restApiResourcesProvider;
    private final Gate commandGate;
    private final MutexLockFactory mutexLockFactory;

    /**
     * 获取所有gateway管理的API并提交给UAMS做权限管理
     */
    @Scheduled(fixedRate = 5000)
    @RequestMapping("/refresh/apis")
    public void refreshApis() {
        //http://localhost:8080/swagger-resources
        //http://localhost:8080/saas-uams/v2/api-docs
        try {
            mutexLockFactory.workWithLock("apiresources", TimeUnit.MILLISECONDS, 500, new OperationWithinLock() {
                @Override
                public void execute() {
                    List<RestApi> apis = restApiResourcesProvider.apis();
                    apis.stream().forEach(t -> {
                        AddApiCommand command = AddApiCommand.builder().verbKey(t.getVerb()).url(t.getUrl()).build();
                        commandGate.dispatch(command);
                    });
                    log.info("Refresh the gateway managed apis by {}", restApiResourcesProvider);
                }
            });
        } catch (MutexLockLockException e) {
            e.printStackTrace();
        }

    }
}
