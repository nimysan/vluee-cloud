package com.vluee.cloud.uams.application.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddApiCommand {
    private String url;
    private String verbKey;
}
