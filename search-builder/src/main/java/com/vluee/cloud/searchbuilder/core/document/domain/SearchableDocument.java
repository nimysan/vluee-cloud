package com.vluee.cloud.searchbuilder.core.document.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * 可 搜索  的文档
 */
@Builder
public class SearchableDocument {

    @Getter
    private String id;

    @Getter
    private String index;

    @Getter
    private String source;


}
