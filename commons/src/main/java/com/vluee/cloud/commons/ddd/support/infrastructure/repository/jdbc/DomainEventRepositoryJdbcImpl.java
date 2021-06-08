package com.vluee.cloud.commons.ddd.support.infrastructure.repository.jdbc;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.support.event.DomainEventRepository;
import com.vluee.cloud.commons.ddd.support.event.SimpleDomainEvent;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * 基于JDBC实现的DomainEvent的存储库
 */
public class DomainEventRepositoryJdbcImpl extends JdbcDaoSupport implements DomainEventRepository {

    public static final String TABLE_NAME = "simple_domain_event";
    private static final String EVENTS_DDL = "CREATE TABLE `simple_domain_event` (\n" +
            "\t`aggregate_id` VARCHAR(128) NOT NULL COLLATE 'utf8_general_ci',\n" +
            "\t`aggregate_status` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
            "\t`version` INT(4) NULL DEFAULT NULL,\n" +
            "\t`content` BLOB NOT NULL,\n" +
            "\t`event_name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
            "\t`event_time` DATETIME NULL DEFAULT NULL,\n" +
            "\t`is_publish` BIT(1) NULL DEFAULT NULL,\n" +
            "\t`retries` INT(11) NULL DEFAULT NULL,\n" +
            "\tPRIMARY KEY (`aggregate_id`) USING BTREE\n" +
            ")";
    private static final String EVENT_COLUMNS = "aggregate_id,aggregate_status,version,content,event_name,event_time,is_publish,retries";
    private static final String DEF_INSERT_EVENT = "insert into " + TABLE_NAME + " (" + EVENT_COLUMNS + ") values (?,?,?,?,?,?,?,?)";
    private static final String DEF_FIND_UNPUBLISHED_EVENTS = " select " + EVENT_COLUMNS + " from " + TABLE_NAME + " where is_publish=0 and retries<=3";
    private static final String DEF_LOAD_EVENT = " select " + EVENT_COLUMNS + " from " + TABLE_NAME + " where aggregate_id=?";


    public DomainEventRepositoryJdbcImpl(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        setJdbcTemplate(new JdbcTemplate(dataSource));
        initTable();
    }

    private void initTable() {
        try {
            getJdbcTemplate().execute(EVENTS_DDL);
        } catch (BadSqlGrammarException e) {
            if (e.getMessage().contains("Table 'simple_domain_event' already exists")) {
                logger.info("The simple_domain_event is already exists");
            } else {
                throw new RuntimeException("Failed to initialize the simple_domain_events table, please refer to " + EVENTS_DDL + ". You can create it by yourself before start application.");
            }
        }
    }

    private static final RowMapper<SimpleDomainEvent> SIMPLE_DOMAIN_EVENT_ROW_MAPPER = new RowMapper<SimpleDomainEvent>() {
        @Override
        public SimpleDomainEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
            return SimpleDomainEvent.builder()
                    .aggregateId(new AggregateId(rs.getString(1)))
                    .isArchive(rs.getBoolean(2))
                    .version(rs.getLong(3))
                    .content(blobToString(rs.getBlob(4)))
                    .eventName(rs.getString(5))
                    .eventTime(rs.getTime(6))
                    .isArchive(rs.getBoolean(7))
                    .retries(rs.getInt(8))
                    .build();
        }
    };

    private static String blobToString(Blob b) throws SQLException {
        try {
            return new String(b.getBytes(1, (int) b.length()), "UTF-8");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new SQLException("Blob convert to String get encoding problem", unsupportedEncodingException);
        }
    }

    @Override
    public void save(SimpleDomainEvent domainEvent) {
        getJdbcTemplate().update(DEF_INSERT_EVENT, domainEvent.getAggregateId().getId(), domainEvent.isArchive(), domainEvent.getVersion(), domainEvent.getContent(), domainEvent.getEventName(), domainEvent.getEventTime(), domainEvent.isPublished(), domainEvent.getRetries());
    }

    @Override
    public Collection<SimpleDomainEvent> fetchNonPublishEvents() {
        List<SimpleDomainEvent> query = getJdbcTemplate().query(DEF_FIND_UNPUBLISHED_EVENTS, SIMPLE_DOMAIN_EVENT_ROW_MAPPER);
        return query;
    }

    @Override
    public SimpleDomainEvent load(AggregateId aggregateId) {
        List<SimpleDomainEvent> query = getJdbcTemplate().query(DEF_LOAD_EVENT, SIMPLE_DOMAIN_EVENT_ROW_MAPPER, aggregateId.getId());
        if (query != null && query.size() == 1) {
            return query.get(0);
        } else {
            throw new RuntimeException("Event load failed");
        }
    }
}
