package com.vluee.cloud.auth.interfaces.outbound.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class UserDetailsVO {
    private String password;
    private String username;
    private boolean enable;
    private boolean locked;
    private boolean expired;
    private boolean CredentialsNonExpired;
    private Collection<String> authorities = new ArrayList<>(2);
}