package com.vluee.cloud.auth.core.user.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

import static com.vluee.cloud.commons.common.data.id.LongIdGenerator.DISTRIBUTED_ID_GENERATOR_CLASS_NAME;
import static com.vluee.cloud.commons.common.data.id.LongIdGenerator.ID_GENERATOR_NAME;

@Entity
@NoArgsConstructor
@GenericGenerator(name = ID_GENERATOR_NAME, strategy = DISTRIBUTED_ID_GENERATOR_CLASS_NAME)
public class LoginPassword {

    public LoginPassword(String password, Date expirationTime) {
        this.password = password;
        this.expirationTime = expirationTime;
    }

    @Id
    @GeneratedValue(generator = ID_GENERATOR_NAME)
    private Long id;

    /**
     * 密码过期时间
     */
    @Column(nullable = false)
    private Date expirationTime;

    @Column(nullable = false, length = 255)
    public String password;

    public String getActivePassword() {
        return password;
    }
}
