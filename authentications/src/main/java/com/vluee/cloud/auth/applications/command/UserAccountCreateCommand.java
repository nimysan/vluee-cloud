package com.vluee.cloud.auth.applications.command;

import com.vluee.cloud.commons.cqrs.annotations.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Command
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountCreateCommand implements Serializable {
    @Getter
    private String userId;
    @Getter
    private String username;
    @Getter
    private String password;
}
