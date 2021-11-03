package com.vluee.cloud.searchbuilder.interfaces.listeners;

import com.vluee.cloud.commons.ddd.annotations.event.DomainEventAction;
import com.vluee.cloud.commons.ddd.annotations.event.DomainEventBinding;
import com.vluee.cloud.searchbuilder.core.document.domain.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 监听domain event, 构建搜索文档并存入相应的介质
 */
@DomainEventBinding
@Slf4j
@AllArgsConstructor
public class DomainEventSearchableListener {

    private final SearchableDocumentMetadataParser searchableDocumentMetadataParser;
    private final SearchableDocumentBuilder searchableDocumentBuilder;
    private final SearchableDocumentRepository searchableDocumentRepository;

    @DomainEventAction
    public void buildDocumentFromDomainEvent(Object payload) {
        log.debug("Build searchable document for aggregate: {}", payload);
        SearchableDocumentMetadata metadata = searchableDocumentMetadataParser.parse(payload);
        SearchableDocument document = searchableDocumentBuilder.build(metadata);
        searchableDocumentRepository.save(document);
    }
}
