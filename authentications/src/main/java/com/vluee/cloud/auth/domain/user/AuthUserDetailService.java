package com.vluee.cloud.auth.domain.user;

import com.vluee.cloud.auth.interfaces.outbound.feign.UserService;
import com.vluee.cloud.auth.interfaces.outbound.feign.UserDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 依赖 aistore-user服务
 */
@Service
@Slf4j
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsVO vo = userService.loadUserByUsername(username);
        log.info("--- {} --- ", vo);
        return new User(username, vo.getPassword(), vo.isEnable(), !vo.isExpired(), !vo.isCredentialsNonExpired(), !vo.isLocked(), vo.getAuthorities().stream().map(t -> new SimpleGrantedAuthority(t)).collect(Collectors.toList()));
    }
}
