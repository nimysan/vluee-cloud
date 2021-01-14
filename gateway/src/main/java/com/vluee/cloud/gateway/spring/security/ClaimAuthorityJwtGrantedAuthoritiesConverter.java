package com.vluee.cloud.gateway.spring.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * 取代默认从claims.[scope]获取authorities值得方式， 替换为从claims.[authorities]属性获取，并不再增加SCOPE_前缀
 * <p>
 * Extracts the {@link GrantedAuthority}s from authorities
 * {@link Jwt}.
 *
 * @see JwtGrantedAuthoritiesConverter
 */
public final class ClaimAuthorityJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String DEFAULT_AUTHORITY_PREFIX = "";

    private static final Collection<String> WELL_KNOWN_AUTHORITIES_CLAIM_NAMES = Arrays.asList("authorities");

    private String authorityPrefix = DEFAULT_AUTHORITY_PREFIX;

    private String authoritiesClaimName;


    public ClaimAuthorityJwtGrantedAuthoritiesConverter() {
    }

    /**
     * Extract {@link GrantedAuthority}s from the given {@link Jwt}.
     *
     * @param jwt The {@link Jwt} token
     * @return The {@link GrantedAuthority authorities} read from the token authorities
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : getRoles(jwt)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(this.authorityPrefix + role));
        }
        return grantedAuthorities;
    }

    /**
     * Sets the prefix to use for {@link GrantedAuthority authorities} mapped by this converter.
     * Defaults to {@link ClaimAuthorityJwtGrantedAuthoritiesConverter#DEFAULT_AUTHORITY_PREFIX}.
     *
     * @param authorityPrefix The authority prefix
     */
    public void setAuthorityPrefix(String authorityPrefix) {
        Assert.notNull(authorityPrefix, "authorityPrefix cannot be null");
        this.authorityPrefix = authorityPrefix;
    }

    /**
     * Sets the name of token claim to use for mapping {@link GrantedAuthority authorities} by this converter.
     * Defaults to {@link ClaimAuthorityJwtGrantedAuthoritiesConverter#WELL_KNOWN_AUTHORITIES_CLAIM_NAMES}.
     *
     * @param authoritiesClaimName The token claim name to map authorities
     */
    public void setAuthoritiesClaimName(String authoritiesClaimName) {
        Assert.hasText(authoritiesClaimName, "authoritiesClaimName cannot be empty");
        this.authoritiesClaimName = authoritiesClaimName;
    }

    private String getAuthoritiesClaimName(Jwt jwt) {

        if (this.authoritiesClaimName != null) {
            return this.authoritiesClaimName;
        }

        for (String claimName : WELL_KNOWN_AUTHORITIES_CLAIM_NAMES) {
            if (jwt.containsClaim(claimName)) {
                return claimName;
            }
        }
        return null;
    }

    private Collection<String> getRoles(Jwt jwt) {
        String claimName = getAuthoritiesClaimName(jwt);

        if (claimName == null) {
            return Collections.emptyList();
        }

        Object authorities = jwt.getClaim(claimName);
        if (authorities instanceof String) {
            if (StringUtils.hasText((String) authorities)) {
                return Arrays.asList(((String) authorities).split(" "));
            } else {
                return Collections.emptyList();
            }
        } else if (authorities instanceof Collection) {
            return (Collection<String>) authorities;
        }

        return Collections.emptyList();
    }
}
