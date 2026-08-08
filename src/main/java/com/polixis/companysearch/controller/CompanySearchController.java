package com.polixis.companysearch.controller;

import com.polixis.companysearch.dto.SearchResponse;
import com.polixis.companysearch.service.CompanySearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/companies")
public class CompanySearchController {

    private final CompanySearchService companySearchService;

    public CompanySearchController(
            CompanySearchService companySearchService
    ) {
        this.companySearchService = companySearchService;
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(
            @RequestParam String query,
            @RequestParam(
                    defaultValue = "false"
            ) boolean forceRefresh
    ) throws IOException {

        SearchResponse response =
                companySearchService.search(
                        query,
                        forceRefresh
                );

        return ResponseEntity.ok(response);
    }
}