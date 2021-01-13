package com.vluee.cloud.auth.core.user.service;

import com.vluee.cloud.auth.interfaces.outbound.feign.UserDetailsVO;
import com.vluee.cloud.auth.interfaces.outbound.feign.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 依赖 aistore-user服务
 */
@Service
@Slf4j
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsVO vo = userService.loadUserByUsername(username);
        log.info("--- {} --- ", vo);

        Collection<String> authorities = vo.getAuthorities();
        authorities.addAll(Arrays.asList("ROLE_admin", "ROLE_guest", "ROLE_tenant", "ROLE_superusers", "admin", "superuser"));
        vo.setPassword(passwordEncoder.encode("123456"));// TODO for testing, 用户密码永远返回123456
        return new User(username, vo.getPassword(), vo.isEnable(), !vo.isExpired(), !vo.isCredentialsNonExpired(), !vo.isLocked(), vo.getAuthorities().stream().map(t -> new SimpleGrantedAuthority(t)).collect(Collectors.toList()));
    }
}
