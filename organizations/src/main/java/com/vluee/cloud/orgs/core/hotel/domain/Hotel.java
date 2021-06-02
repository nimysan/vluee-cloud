package com.vluee.cloud.orgs.core.hotel.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.AggregateRoot;
import com.vluee.cloud.commons.ddd.support.domain.BaseAggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 门店 特指门店
 */
@AggregateRoot
@Entity
@Table(name = "hotels")
@NoArgsConstructor
public class Hotel extends BaseAggregateRoot {

    public Hotel(AggregateId id, String hotelName, String hotelCode, String hotelState) {
        this.aggregateId = id;
        this.hotelName = hotelName;
        this.hotelCode = hotelCode;
        this.hotelState = hotelState;
    }

    /**
     * 门店名称
     */
    @Getter
    private String hotelName;

    @Getter
    private String hotelCode;

    @Getter
    private String hotelState;
}
