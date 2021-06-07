package com.vluee.cloud.statistics.core.org;


import java.io.IOException;

public interface HotelGeneralStatisticsRepository {

    void save(HotelGeneralStatistics hotelGeneralStatistics) throws IOException;

    HotelGeneralStatistics load() throws IOException;

}
