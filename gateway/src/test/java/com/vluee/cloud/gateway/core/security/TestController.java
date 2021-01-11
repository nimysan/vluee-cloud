package com.vluee.cloud.gateway.core.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {


    @GetMapping("/options_test")
    public Mono<Void> testController() {
        return Mono.empty();
    }

    @GetMapping("/swagger-ui.html")
    public Mono<Void> mockSwagger(){
        return Mono.empty();
    }


    @GetMapping("/task/{id}")
    public Mono<String> mockGetTask(@PathVariable String id){
        return Mono.just("hello");
    }

    @PostMapping("/task")
    public Mono<String> mockPostTask(){
        return Mono.just("hello");
    }

}
