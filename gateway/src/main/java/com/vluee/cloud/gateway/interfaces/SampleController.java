package com.vluee.cloud.gateway.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/version")
    public String version() {
        return "13";
    }
}
