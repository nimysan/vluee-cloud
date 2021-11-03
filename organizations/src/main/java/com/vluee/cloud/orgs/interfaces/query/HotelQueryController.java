package com.vluee.cloud.orgs.interfaces.query;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.ddd.annotations.domain.Finder;
import com.vluee.cloud.orgs.core.hotel.domain.Hotel;
import com.vluee.cloud.orgs.core.hotel.domain.HotelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@Finder
@AllArgsConstructor
@Slf4j
public class HotelQueryController {
    private final HotelRepository hotelRepository;

    @RequestMapping("/hotels/{id}")
    public Hotel loadHotel(@PathVariable String id) {
        return hotelRepository.load(new AggregateId(id));
    }
}
