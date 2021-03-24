package com.vluee.cloud.auth.core.sms.domain;

import cn.hutool.core.util.RandomUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SmsCode {

    @Getter
    private int codeValue;

    @Getter
    private String clientId;

    @Getter
    private String mobile;

    public SmsCode(String mobile) {
        this.clientId = RandomUtil.randomString(20);
        this.codeValue = RandomUtil.randomInt(1000, 9999); //4位短信验证码
        this.mobile = mobile;
    }

    public void send() {
        log.info("Send sms code {}", codeValue);
        //TODO 正式发送出去
    }
}
