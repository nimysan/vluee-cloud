package com.vluee.cloud.statistics.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddChildBrandCommand {
    private String tenantId;
    private String parentBrandId;
    private String brandName;
    private String brandCode; //品牌编码
}
