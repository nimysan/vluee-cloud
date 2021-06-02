package com.vluee.cloud.statistics.application.listeners;

import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.statistics.core.org.HotelGeneralStatistics;

@ApplicationService
public class StatisticsApplicationService {

    public HotelGeneralStatistics hotelGeneralStatistics = new HotelGeneralStatistics();

    public HotelGeneralStatistics getHotelGeneralStatistics() {
        return hotelGeneralStatistics;
    }
}
