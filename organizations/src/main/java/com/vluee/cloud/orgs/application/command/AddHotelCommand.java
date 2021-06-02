package com.vluee.cloud.orgs.application.command;

import com.vluee.cloud.commons.cqrs.annotations.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Command
@AllArgsConstructor
public class AddHotelCommand implements Serializable {
    private String hotelName, hotelCode, hotelState;
}
