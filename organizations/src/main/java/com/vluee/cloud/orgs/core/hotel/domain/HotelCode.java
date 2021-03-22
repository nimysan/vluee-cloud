package com.vluee.cloud.orgs.core.hotel.domain;

public class HotelCode {

    private HotelCodeType codeType;

    private String code;

    enum HotelCodeType {
        PROJECT_CODE, CONTRACT_CODE
    }
}
