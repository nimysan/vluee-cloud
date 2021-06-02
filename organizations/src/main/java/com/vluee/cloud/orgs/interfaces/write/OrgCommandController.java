package com.vluee.cloud.orgs.interfaces.write;

import com.vluee.cloud.commons.cqrs.command.Gate;
import com.vluee.cloud.orgs.application.command.AddHotelCommand;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@AllArgsConstructor
public class OrgCommandController {

    private final Gate commandGate;

    /**
     * 增加一个酒店
     *
     * @param hotelName
     * @param hotelCode
     * @param hotelState
     */
    @PostMapping("/hotels")
    public void addHotel(@RequestParam String hotelName, @RequestParam String hotelCode, @RequestParam String hotelState) {
        commandGate.dispatch(new AddHotelCommand(hotelName, hotelCode, hotelState));
    }

}

