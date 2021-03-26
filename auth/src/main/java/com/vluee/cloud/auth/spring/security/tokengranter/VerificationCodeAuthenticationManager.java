package com.vluee.cloud.auth.spring.security.tokengranter;

import com.vluee.cloud.auth.core.sms.domain.SmsCode;
import com.vluee.cloud.auth.core.sms.domain.SmsCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;

import java.util.Map;
import java.util.Optional;


@Slf4j
public class VerificationCodeAuthenticationManager implements AuthenticationManager {
    private final UserDetailsService userDetailsService;
    private final SmsCodeRepository smsCodeRepository;

    public VerificationCodeAuthenticationManager(UserDetailsService userDetailsService, SmsCodeRepository smsCodeRepository) {
        this.userDetailsService = userDetailsService;
        this.smsCodeRepository = smsCodeRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;
            if (isCodeMatched(upAuth)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername((String) upAuth.getPrincipal());
                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                        userDetails, authentication.getCredentials(),
                        userDetails.getAuthorities());
                result.setDetails(authentication.getDetails());
                return result;
            } else {
                throw new InvalidGrantException("username and the verification_code is not matched. the sms code is ---> " + upAuth.getCredentials());
            }
        }
        throw new InvalidGrantException("VerificationCodeAuthenticationManager setup is error, please call the programmer to check it");
    }

    /**
     *  验证 唯一ID,手机号码和验证码 都相等
     *
     * @param auth
     * @return
     */
    private boolean isCodeMatched(UsernamePasswordAuthenticationToken auth) {
        Map<String, Object> details = (Map<String, Object>) auth.getDetails();
        Optional<SmsCode> smsCode = smsCodeRepository.loadByUserClientId((String) details.get("userClientId"));
        if (smsCode.isPresent()) {
            SmsCode code = smsCode.get();
            return code.isNotExpired() && code.getMobile().equals(details.get("mobile")) && Integer.toString(code.getCodeValue()).equals(auth.getCredentials());
        } else {
            return false;
        }
    }
}
