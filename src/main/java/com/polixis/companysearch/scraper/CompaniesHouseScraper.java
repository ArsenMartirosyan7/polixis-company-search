package com.polixis.companysearch.scraper;

import com.polixis.companysearch.config.ScraperProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CompaniesHouseScraper {

    private final ScraperProperties properties;

    public CompaniesHouseScraper(ScraperProperties properties) {
        this.properties = properties;
    }

    public List<CompanySearchResult> searchCompanies(String query) throws IOException {

        String encodedQuery = URLEncoder.encode(
                query,
                StandardCharsets.UTF_8
        );

        String searchUrl =
                properties.getBaseUrl()
                        + "/search/companies?q="
                        + encodedQuery;

        Document document = Jsoup.connect(searchUrl)
                .userAgent(properties.getUserAgent())
                .timeout(10_000)
                .get();

        Map<String, CompanySearchResult> results = new LinkedHashMap<>();

        for (Element link : document.select("a[href^=/company/]")) {

            String path = link.attr("href");

            String companyNumber = extractCompanyNumber(path);
            String name = link.text().trim();

            if (companyNumber == null || name.isBlank()) {
                continue;
            }

            results.putIfAbsent(
                    companyNumber,
                    new CompanySearchResult(
                            companyNumber,
                            name,
                            path
                    )
            );

            if (results.size() >= properties.getMaxCompanies()) {
                break;
            }
        }

        return new ArrayList<>(results.values());
    }

    private String extractCompanyNumber(String path) {

        String prefix = "/company/";

        if (!path.startsWith(prefix)) {
            return null;
        }

        String remaining = path.substring(prefix.length());

        int slashIndex = remaining.indexOf('/');

        if (slashIndex >= 0) {
            remaining = remaining.substring(0, slashIndex);
        }

        return remaining.isBlank() ? null : remaining;
    }
}
