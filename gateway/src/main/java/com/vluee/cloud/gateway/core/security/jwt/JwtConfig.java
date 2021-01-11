package com.vluee.cloud.gateway.core.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;

@Configuration
public class JwtConfig {

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtAuthenticationAuthorityConverter() {
        return new ClaimAuthorityJwtGrantedAuthoritiesConverter();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationTokenConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtAuthenticationAuthorityConverter());
        return converter;
    }

}
