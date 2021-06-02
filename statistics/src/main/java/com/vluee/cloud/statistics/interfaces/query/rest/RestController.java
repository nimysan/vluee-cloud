package com.vluee.cloud.statistics.interfaces.query.rest;

import com.vluee.cloud.statistics.application.listeners.StatisticsApplicationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/statistics")
public class RestController {

    private final StatisticsApplicationService statisticsApplicationService;

    @ApiOperation("test接口说明")
    @GetMapping("/test")
    public String test() {
        return "1";
    }


    @GetMapping("hotels")
    public int hotelGeneralStatistics() {
        return statisticsApplicationService.getHotelGeneralStatistics().getTotalHotelSize();
    }
}
