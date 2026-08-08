package com.polixis.companysearch.dto;

import java.time.LocalDate;

public record OfficerResponse(
        String name,
        String role,
        LocalDate appointedOn,
        LocalDate resignedOn
) {
}

