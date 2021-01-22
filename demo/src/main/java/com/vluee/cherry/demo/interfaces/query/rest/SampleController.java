package com.vluee.cherry.demo.interfaces.query.rest;

import cn.hutool.core.net.NetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sample")
public class SampleController {

    @GetMapping("simple")
    public String simple() {
        return "13";
    }

    @GetMapping("object")
    public SampleVo object() {
        return new SampleVo(NetUtil.getLocalMacAddress(), "Sample information for " + NetUtil.getLocalhostStr());
    }

    @GetMapping("exception")
    public String exception() {
        throw new RuntimeException("案例-这是人为设置的错误信息");
    }

    @Data
    @AllArgsConstructor
    public static class SampleVo {
        private String foo;
        private String bar;
    }
}
