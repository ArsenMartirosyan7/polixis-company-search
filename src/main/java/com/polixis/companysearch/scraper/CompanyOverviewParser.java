package com.polixis.companysearch.scraper;

import com.polixis.companysearch.entity.Company;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class CompanyOverviewParser {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("d MMMM uuuu", Locale.ENGLISH);

    public Company parse(
            Document document,
            CompanySearchResult searchResult
    ) {

        Company company = new Company();

        company.setCompanyNumber(searchResult.companyNumber());
        company.setName(searchResult.name());

        company.setRegisteredAddress(
                textOf(document, "#registered-office-address")
        );

        company.setStatus(
                textOf(document, "#company-status")
        );

        company.setCompanyType(
                textOf(document, "#company-type")
        );

        company.setIncorporatedOn(
                parseDate(textOf(document, "#company-creation-date"))
        );

        return company;
    }

    private String textOf(Document document, String selector) {

        Element element = document.selectFirst(selector);

        if (element == null) {
            return null;
        }

        String text = element.text().trim();

        return text.isBlank() ? null : text;
    }

    private LocalDate parseDate(String value) {

        if (value == null) {
            return null;
        }

        try {
            return LocalDate.parse(value, DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            return null;
        }
    }
}
