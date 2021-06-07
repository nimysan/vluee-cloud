package com.vluee.cloud.orgs.core.hotel.domain.events;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class HotelAddedEvent implements Serializable {

    private AggregateId hotelId;

}