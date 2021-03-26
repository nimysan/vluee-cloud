package com.vluee.cloud.auth.interfaces.write;

import com.vluee.cloud.auth.applications.command.UserAccountCreateCommand;
import com.vluee.cloud.auth.core.sms.domain.SmsCode;
import com.vluee.cloud.auth.core.sms.domain.SmsCodeRepository;
import com.vluee.cloud.commons.cqrs.command.Gate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@AllArgsConstructor
@Transactional
public class UserAccountController {

    private final Gate gate;

    private final SmsCodeRepository smsCodeRepository;

    /**
     * @param username 用户名
     * @param password 密码明文
     */
    @PostMapping("/user-accounts")
    public void createAccount(@RequestParam String userId, @RequestParam String username, @RequestParam String password) {
        gate.dispatch(new UserAccountCreateCommand(userId, username, password));
    }

    @PostMapping("/sms/{mobile}")
    public void fetchSmsCode(@PathVariable String mobile, HttpServletResponse response) throws IOException {
        SmsCode smsCode = new SmsCode(mobile);
        smsCode.send();
        smsCodeRepository.save(smsCode);
        //发送短信验证码
        response.setStatus(HttpStatus.OK.value());
        response.addHeader("X-CLIENT-IDENTIFIER", smsCode.getClientId()); //唯一ID
        response.getOutputStream().flush();
    }
}
