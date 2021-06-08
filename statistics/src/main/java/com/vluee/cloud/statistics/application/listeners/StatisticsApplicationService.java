package com.vluee.cloud.statistics.application.listeners;

import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.statistics.core.org.HotelGeneralStatistics;
import com.vluee.cloud.statistics.core.org.HotelGeneralStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@ApplicationService
public class StatisticsApplicationService {

    @Autowired
    private HotelGeneralStatisticsRepository hotelGeneralStatisticsRepository;

    private HotelGeneralStatistics hotelGeneralStatistics;

    public HotelGeneralStatistics getHotelGeneralStatistics() {
        try {
            if (hotelGeneralStatistics == null) {
                hotelGeneralStatistics = hotelGeneralStatisticsRepository.load();
            }
            return hotelGeneralStatistics;
        } catch (Exception e) {
            throw new RuntimeException("Failed ", e);
        }


    }

    public void save() {
        try {
            hotelGeneralStatisticsRepository.save(this.hotelGeneralStatistics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
