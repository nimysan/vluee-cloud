package com.vluee.cloud.uams.core.brand.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
public class Brand {

    private @NotBlank String tenantId;

    private @NotBlank String tenantName;

    private @NotBlank String brandId;

    private @NotBlank String brandName;
}
