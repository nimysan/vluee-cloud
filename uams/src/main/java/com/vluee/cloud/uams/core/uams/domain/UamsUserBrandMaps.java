package com.vluee.cloud.uams.core.uams.domain;

import com.vluee.cloud.commons.common.data.AuditAware;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "uams_user_brand_maps")
@NoArgsConstructor
public class UamsUserBrandMaps extends AuditAware {

    public UamsUserBrandMaps(String userId, String brandId) {
        this.id = new UamsUserBrandKey(userId, brandId);
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "user_id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "brand_id", column = @Column(name = "brand_id"))
    })
    private UamsUserBrandKey id;

    public String getBrandId() {
        return this.id.getBrandId();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Embeddable
    public static class UamsUserBrandKey implements Serializable {

        @Column(name = "user_id", nullable = false)
        private String userId;

        @Column(name = "brand_id", nullable = false)
        private String brandId;
    }

}
