package com.vluee.cloud.tenants.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddBrandCommand {
    private String tenantId;
    private String brandName;
    private String brandCode; //品牌编码
}
