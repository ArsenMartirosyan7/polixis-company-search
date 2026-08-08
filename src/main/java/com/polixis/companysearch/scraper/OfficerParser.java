package com.polixis.companysearch.scraper;

import com.polixis.companysearch.entity.Company;
import com.polixis.companysearch.entity.Officer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OfficerParser {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("d MMMM uuuu", Locale.ENGLISH);

    public List<Officer> parse(Document document, Company company) {

        List<Officer> officers = new ArrayList<>();

        for (Element nameElement : document.select("[id^=officer-name-]")) {

            String suffix = nameElement.id()
                    .substring("officer-name-".length());

            String name = nameElement.text().trim();

            String role = textOf(
                    document,
                    "#officer-role-" + suffix
            );

            String appointedOn = textOf(
                    document,
                    "#officer-appointed-on-" + suffix
            );

            String resignedOn = textOf(
                    document,
                    "#officer-resigned-on-" + suffix
            );

            if (name.isBlank()) {
                continue;
            }

            Officer officer = new Officer();

            officer.setName(name);
            officer.setRole(role);
            officer.setAppointedOn(parseDate(appointedOn));
            officer.setResignedOn(parseDate(resignedOn));

            company.addOfficer(officer);
            officers.add(officer);
        }

        return officers;
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
