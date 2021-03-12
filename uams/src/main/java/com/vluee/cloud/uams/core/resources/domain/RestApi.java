package com.vluee.cloud.uams.core.resources.domain;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;

@ToString
@NoArgsConstructor // for jpa
public class RestApi {

    public static final String ALL_VERB = "*";

    @Getter
    private String verb;

    @Getter
    private String url;

    public RestApi(@NotNull String verb, @NotNull String url) {
        this.verb = StringUtils.upperCase(verb);
        this.url = StringUtils.upperCase(url);
    }

    public RestApi(@NotNull String url) {
        this.verb = ALL_VERB;
        this.url = StringUtils.upperCase(url);
    }

    @Override
    public String toString() {
        return "RestApi{" +
                "verb='" + verb + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestApi restApi = (RestApi) o;
        return Objects.equal(verb, restApi.verb) && Objects.equal(url, restApi.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(verb, url);
    }
}
