package com.vluee.cloud.auth.core.sms.domain;

import java.util.Optional;

public interface SmsCodeRepository {

    public Optional<SmsCode> loadByUserClientId(final String userClientId);

    void save(SmsCode smsCode);

}
