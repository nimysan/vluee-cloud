package com.vluee.cloud.statistics.core.org;


import lombok.Getter;

public class HotelGeneralStatistics {

    public HotelGeneralStatistics(String size) {
        this.totalHotelSize = Integer.parseInt(size);
    }

    public HotelGeneralStatistics() {
        this.totalHotelSize = 0;
    }

    @Getter
    private int totalHotelSize = 0;

    public void increment() {
        this.totalHotelSize = this.totalHotelSize + 1;
    }
}
