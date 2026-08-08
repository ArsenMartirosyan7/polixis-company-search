package com.polixis.companysearch.scraper;

import com.polixis.companysearch.entity.Company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyOverviewParserTest {

    private final CompanyOverviewParser parser =
            new CompanyOverviewParser();

    @Test
    void shouldParseCompanyOverview() {

        String html = """
                <html>
                    <body>
                        <div id="registered-office-address">
                            Tesco House, London
                        </div>

                        <dd id="company-status">
                            Active
                        </dd>

                        <dd id="company-type">
                            Public limited Company
                        </dd>

                        <dd id="company-creation-date">
                            27 November 1947
                        </dd>
                    </body>
                </html>
                """;

        Document document = Jsoup.parse(html);

        CompanySearchResult searchResult =
                new CompanySearchResult(
                        "00445790",
                        "TESCO PLC",
                        "/company/00445790"
                );

        Company company =
                parser.parse(document, searchResult);

        assertEquals(
                "00445790",
                company.getCompanyNumber()
        );

        assertEquals(
                "TESCO PLC",
                company.getName()
        );

        assertEquals(
                "Active",
                company.getStatus()
        );

        assertEquals(
                "Public limited Company",
                company.getCompanyType()
        );

        assertEquals(
                LocalDate.of(1947, 11, 27),
                company.getIncorporatedOn()
        );

        assertEquals(
                "Tesco House, London",
                company.getRegisteredAddress()
        );
    }
}
