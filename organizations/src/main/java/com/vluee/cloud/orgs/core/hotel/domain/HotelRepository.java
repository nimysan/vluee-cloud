package com.vluee.cloud.orgs.core.hotel.domain;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;

public interface HotelRepository {

    void save(Hotel hotel);

    Hotel load(AggregateId id);
}
