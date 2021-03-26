package com.vluee.cloud.auth.infrastructure.repo.inmem;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.vluee.cloud.auth.core.sms.domain.SmsCode;
import com.vluee.cloud.auth.core.sms.domain.SmsCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class InMemSmsCodeRepository implements SmsCodeRepository {

    private Map<String, SmsCode> cached = new HashMap<>();

    @Override
    public Optional<SmsCode> loadByUserClientId(String userClientId) {
        return Optional.ofNullable(cached.get(userClientId));
    }

    public void save(SmsCode smsCode) {
        if (StringUtils.isBlank(smsCode.getClientId())) {
            throw new RuntimeException("SmsCode must have a client id before call save");
        }
        cached.put(smsCode.getClientId(), smsCode);
    }
}
