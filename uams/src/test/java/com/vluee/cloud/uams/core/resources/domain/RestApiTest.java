package com.vluee.cloud.uams.core.resources.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RestApiTest {

    @Test
    public void testUrlNotCaseSensitive() {
        String url = "/hotels";
        String urlInUpperCase = "/Hotels";
        RestApi api1 = new RestApi("GET", url);
        RestApi api2 = new RestApi("GET", urlInUpperCase);

        Assertions.assertEquals(api1, api2);
    }

}