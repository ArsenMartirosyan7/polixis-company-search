package com.polixis.companysearch.service;

import com.polixis.companysearch.entity.Company;
import com.polixis.companysearch.entity.SearchQuery;
import com.polixis.companysearch.repository.CompanyRepository;
import com.polixis.companysearch.repository.SearchQueryRepository;
import com.polixis.companysearch.scraper.CompaniesHouseScraper;
import com.polixis.companysearch.scraper.CompanySearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CompanySearchService {

    private final CompaniesHouseScraper scraper;
    private final CompanyRepository companyRepository;
    private final SearchQueryRepository searchQueryRepository;
    private final long cacheTtlHours;

    public CompanySearchService(
            CompaniesHouseScraper scraper,
            CompanyRepository companyRepository,
            SearchQueryRepository searchQueryRepository,
            @Value("${cache.ttl-hours}") long cacheTtlHours
    ) {
        this.scraper = scraper;
        this.companyRepository = companyRepository;
        this.searchQueryRepository = searchQueryRepository;
        this.cacheTtlHours = cacheTtlHours;
    }

    @Transactional
    public List<Company> search(String query) throws IOException {

        String normalizedQuery = normalizeQuery(query);

        Optional<SearchQuery> cachedSearch =
                searchQueryRepository.findByQuery(normalizedQuery);

        if (cachedSearch.isPresent()
                && isFresh(cachedSearch.get())) {

            return cachedSearch.get().getCompanies();
        }

        List<CompanySearchResult> searchResults =
                scraper.searchCompanies(normalizedQuery);

        List<Company> companies = new ArrayList<>();

        for (CompanySearchResult result : searchResults) {

            Company scrapedCompany =
                    scraper.fetchCompanyOverview(result);

            scraper.fetchOfficers(scrapedCompany);

            Company savedCompany =
                    saveOrUpdateCompany(scrapedCompany);

            companies.add(savedCompany);
        }

        SearchQuery searchQuery =
                cachedSearch.orElseGet(SearchQuery::new);

        searchQuery.setQuery(normalizedQuery);
        searchQuery.setFetchedAt(LocalDateTime.now());
        searchQuery.setCompanies(companies);

        searchQueryRepository.save(searchQuery);

        return companies;
    }

    private Company saveOrUpdateCompany(Company scrapedCompany) {

        Optional<Company> existing =
                companyRepository.findByCompanyNumber(
                        scrapedCompany.getCompanyNumber()
                );

        if (existing.isEmpty()) {
            return companyRepository.save(scrapedCompany);
        }

        Company company = existing.get();

        company.setName(scrapedCompany.getName());
        company.setStatus(scrapedCompany.getStatus());
        company.setCompanyType(scrapedCompany.getCompanyType());
        company.setIncorporatedOn(scrapedCompany.getIncorporatedOn());
        company.setDissolvedOn(scrapedCompany.getDissolvedOn());
        company.setRegisteredAddress(
                scrapedCompany.getRegisteredAddress()
        );

        company.getOfficers().clear();

        scrapedCompany.getOfficers()
                .forEach(company::addOfficer);

        return companyRepository.save(company);
    }

    private boolean isFresh(SearchQuery searchQuery) {

        LocalDateTime expirationTime =
                searchQuery.getFetchedAt()
                        .plusHours(cacheTtlHours);

        return expirationTime.isAfter(LocalDateTime.now());
    }

    private String normalizeQuery(String query) {

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "Search query must not be blank"
            );
        }

        return query.trim()
                .toLowerCase(Locale.ROOT);
    }
}

