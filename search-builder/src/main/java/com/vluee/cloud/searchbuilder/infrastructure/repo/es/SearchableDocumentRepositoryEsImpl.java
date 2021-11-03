package com.vluee.cloud.searchbuilder.infrastructure.repo.es;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.searchbuilder.core.document.domain.SearchableDocument;
import com.vluee.cloud.searchbuilder.core.document.domain.SearchableDocumentRepository;
import com.vluee.cloud.searchbuilder.core.document.exception.SearchableDocumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

import static org.elasticsearch.action.support.WriteRequest.RefreshPolicy.IMMEDIATE;

/**
 * 基于ES的存储库的实现
 */
@AllArgsConstructor
@DomainRepositoryImpl
@Slf4j
public class SearchableDocumentRepositoryEsImpl implements SearchableDocumentRepository {

    private final RestHighLevelClient highLevelClient;

    @Override
    public void save(SearchableDocument searchableDocument) {
        try {
            IndexRequest request = new IndexRequest(searchableDocument.getIndex())
                    .id(searchableDocument.getId()).source("", XContentType.JSON)
                    .setRefreshPolicy(IMMEDIATE);
            IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
            log.debug("Document Build Response is {}", response);
        } catch (IOException e) {
            throw new SearchableDocumentException("Failed to build document.", e);
        }
    }
}
