package com.vluee.cloud.gateway.spring.security;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BearerTokenHeaderTEst {

    private static final Pattern authorizationPattern = Pattern.compile(
            "^Bearer (?<token>[a-zA-Z0-9-._~+/]+)=*$",
            Pattern.CASE_INSENSITIVE);


    @Test
    public void test() {
        assertTrue(m("Bearer xx="));
        assertTrue(m("Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDc0ODM0NzEsInVzZXJfbmFtZSI6IjMzMyIsImF1dGhvcml0aWVzIjpbIuWIhuWFrOWPuOe7j-eQhiJdLCJqdGkiOiIyOTkxN2ZmMy00MGJjLTQxZGQtODhiNS1jMDE2N2QxMmI3ZTkiLCJjbGllbnRfaWQiOiJ3eGFwcCIsInNjb3BlIjpbImFsbCJdfQ.JRKNMGJljZL3nLKMsdp843pcfC4V2ptDPDb-DirZkokxk3OxQy54MN2ZnYaPG0APej8TzmP5A_WlBpsg5RjJF9aB_GEI72i95JkDOeIXwAhT7r6ctrnVJwnZwpA5KCs1AsDoE09XlDxAM9TTsZ4_Nt1HvLVqXLZrSg-Dtc0lHzo\",\"refreshToken\":\"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIzMzMiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiMjk5MTdmZjMtNDBiYy00MWRkLTg4YjUtYzAxNjdkMTJiN2U5IiwiZXhwIjoxNjA4MDAxODcxLCJhdXRob3JpdGllcyI6WyLliIblhazlj7jnu4_nkIYiXSwianRpIjoiMDhlMmI4YmYtMTM5Ni00YWU0LWFiN2MtZGY3MzE1YTMzNmVlIiwiY2xpZW50X2lkIjoid3hhcHAifQ.atQ-dx6VYxicJfNcn1LjOpPdgGhDm5xYf1x6whc6aUYSUGYik-o15eTWF0tIi9aOYbZF7t0nMvBEuTIGiwk27pSjO77u4C7MSv8P-uODBlMyGZEcPDKCuu_DnDIVroHYzWH3fPh8xbHd5eKDRK7ldsW_yX67XA3pPasuyWXvvKY"));
    }

    private boolean m(String target) {
        return authorizationPattern.matcher(target).matches();
    }

}

