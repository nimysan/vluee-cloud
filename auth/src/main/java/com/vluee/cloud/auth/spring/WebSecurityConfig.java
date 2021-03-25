package com.vluee.cloud.auth.spring;

import com.vluee.cloud.auth.spring.security.filter.IntegrationAuthenticationProcessingFilter;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers("/rsa/publicKey").permitAll()
                .antMatchers("/oauth/rest_token").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers(HttpMethod.POST, "/user-accounts/**").permitAll()//用户注册
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/sms/*").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IntegrationAuthenticationProcessingFilter integrationAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        IntegrationAuthenticationProcessingFilter integrationAuthenticationProcessingFilter = new IntegrationAuthenticationProcessingFilter();
//        integrationAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        return integrationAuthenticationProcessingFilter;
    }
}
