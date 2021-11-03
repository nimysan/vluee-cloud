package com.vluee.cloud.commons.common.search;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DocumentObject {

    public static final String STATUS_OK = "0";

    private String id;
    private String source;
    private String documentIndex;
    private String message;
    private String status;
}
