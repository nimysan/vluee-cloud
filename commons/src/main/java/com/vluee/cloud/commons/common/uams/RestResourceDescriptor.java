package com.vluee.cloud.commons.common.uams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * REST类型的资源描述
 */
@Data
@AllArgsConstructor
@Builder
public class RestResourceDescriptor implements ResourceDescriptor {

    /**
     * 等价于Http Method, 如果为 *, 允许所有方法
     */
    private @NotNull String verb;
    private @NotNull String resource;
    private @NotNull String name;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestResourceDescriptor that = (RestResourceDescriptor) o;
        return Objects.equals(verb, that.verb) &&
                Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verb, resource);
    }
}
