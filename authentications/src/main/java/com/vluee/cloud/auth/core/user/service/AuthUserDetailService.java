package com.vluee.cloud.auth.core.user.service;

import com.vluee.cloud.auth.core.uams.service.UamsFacade;
import com.vluee.cloud.auth.interfaces.outbound.feign.UserDetailsVO;
import com.vluee.cloud.auth.interfaces.outbound.feign.UserServiceProxy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 依赖 aistore-user服务
 */
@Service
@Slf4j
@AllArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

    private final UserServiceProxy userService;

    private final UamsFacade uamsFacade;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsVO vo;
        try {
            vo = userService.loadUserByUsername(username);
        } catch (Exception e) {
            vo = new UserDetailsVO();
            vo.setUsername(username).setEnable(true).setExpired(false).setLocked(false).setCredentialsNonExpired(false).setPassword("");
        }


        Set<String> strings = new HashSet<>(); //uamsFacade.listAuthorities("1111", username);
        Collection<String> authorities = vo.getAuthorities();
        authorities.addAll(strings);
        // 授予游客角色， 所有人默认都授予游客角色
        authorities.addAll(Arrays.asList("guest"));
        log.info("--- {} --- ", vo);
        vo.setPassword(passwordEncoder.encode("123456"));// TODO for testing, 用户密码永远返回123456
        return new User(username, vo.getPassword(), vo.isEnable(), !vo.isExpired(), !vo.isCredentialsNonExpired(), !vo.isLocked(), vo.getAuthorities().stream().map(t -> new SimpleGrantedAuthority(t)).collect(Collectors.toList()));
    }
}
