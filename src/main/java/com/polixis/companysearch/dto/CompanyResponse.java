package com.polixis.companysearch.dto;

import java.time.LocalDate;
import java.util.List;

public record CompanyResponse(
        String companyNumber,
        String name,
        String status,
        String companyType,
        LocalDate incorporatedOn,
        LocalDate dissolvedOn,
        String registeredAddress,
        List<OfficerResponse> officers
) {
}
