package com.vluee.cloud.statistics.core.org;


import lombok.Getter;

public class HotelGeneralStatistics {

    @Getter
    private int totalHotelSize;

    public void increment() {
        this.totalHotelSize = this.totalHotelSize + 1;
    }
}
