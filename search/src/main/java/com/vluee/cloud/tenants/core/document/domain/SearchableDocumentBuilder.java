package com.vluee.cloud.tenants.core.document.domain;

/**
 * 文档构建器，根据输入描述 构建一个可搜索的 文档
 */
public interface SearchableDocumentBuilder {
    public SearchableDocument build(SearchableDocumentMetadata metadata);
}
