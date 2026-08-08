package com.polixis.companysearch.dto;

import com.polixis.companysearch.entity.Company;
import com.polixis.companysearch.entity.Officer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyMapper {

    public CompanyResponse toResponse(Company company) {

        List<OfficerResponse> officers = company.getOfficers()
                .stream()
                .map(this::toOfficerResponse)
                .toList();

        return new CompanyResponse(
                company.getCompanyNumber(),
                company.getName(),
                company.getStatus(),
                company.getCompanyType(),
                company.getIncorporatedOn(),
                company.getDissolvedOn(),
                company.getRegisteredAddress(),
                officers
        );
    }

    public List<CompanyResponse> toResponses(List<Company> companies) {

        return companies.stream()
                .map(this::toResponse)
                .toList();
    }

    private OfficerResponse toOfficerResponse(Officer officer) {

        return new OfficerResponse(
                officer.getName(),
                officer.getRole(),
                officer.getAppointedOn(),
                officer.getResignedOn()
        );
    }
}
