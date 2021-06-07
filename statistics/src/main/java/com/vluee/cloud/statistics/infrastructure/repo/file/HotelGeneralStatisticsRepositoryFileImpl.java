package com.vluee.cloud.statistics.infrastructure.repo.file;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.statistics.core.org.HotelGeneralStatistics;
import com.vluee.cloud.statistics.core.org.HotelGeneralStatisticsRepository;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@DomainRepositoryImpl
public class HotelGeneralStatisticsRepositoryFileImpl implements HotelGeneralStatisticsRepository {

    private String path = "c:\\temp\\test_hotel";

    @Override
    public void save(HotelGeneralStatistics hotelGeneralStatistics) throws IOException {
        IOUtils.write(hotelGeneralStatistics.getTotalHotelSize() + "", new FileOutputStream(new File(path)));
    }

    @Override
    public HotelGeneralStatistics load() throws IOException {
        String s = IOUtils.readLines(new FileInputStream(new File(path))).get(0);
        HotelGeneralStatistics hs = new HotelGeneralStatistics();
        return hs;
    }
}
