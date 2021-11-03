package com.vluee.cloud.searchbuilder.core.document.domain;

import com.vluee.cloud.commons.common.search.DocumentObject;
import com.vluee.cloud.searchbuilder.core.document.exception.SearchableDocumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 文档构建器，根据输入描述 构建一个可搜索的 文档
 */
@Component
@AllArgsConstructor
@Slf4j
public class SearchableDocumentBuilder {

    private final RestTemplate restTemplate;

    public SearchableDocument build(SearchableDocumentMetadata metadata) {
        DocumentObject documentObject = restTemplate.getForObject(metadata.getNativeUrl(), DocumentObject.class);

        if (DocumentObject.STATUS_OK.equalsIgnoreCase(documentObject.getStatus())) {
            return SearchableDocument.builder().id(documentObject.getId()).index(documentObject.getDocumentIndex()).source(documentObject.getSource()).build();
        }
        log.info("----- {} -----", documentObject);
        throw new SearchableDocumentException("Failed to build document");
    }
}
