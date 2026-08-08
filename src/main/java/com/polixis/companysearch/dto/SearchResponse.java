package com.polixis.companysearch.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SearchResponse(
        String query,
        boolean cached,
        LocalDateTime fetchedAt,
        int count,
        List<CompanyResponse> companies
) {
}
