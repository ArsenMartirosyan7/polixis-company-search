package com.polixis.companysearch.scraper;

import com.polixis.companysearch.entity.Company;
import com.polixis.companysearch.entity.Officer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OfficerParserTest {

    private final OfficerParser parser =
            new OfficerParser();

    @Test
    void shouldParseOfficer() {

        String html = """
                <html>
                    <body>
                        <h2 id="officer-name-1">
                            JOHN SMITH
                        </h2>

                        <dd id="officer-role-1">
                            Director
                        </dd>

                        <dd id="officer-appointed-on-1">
                            10 May 2020
                        </dd>
                    </body>
                </html>
                """;

        Document document = Jsoup.parse(html);

        Company company = new Company();

        company.setCompanyNumber("00445790");
        company.setName("TESCO PLC");

        List<Officer> officers =
                parser.parse(document, company);

        assertEquals(1, officers.size());

        Officer officer = officers.getFirst();

        assertEquals(
                "JOHN SMITH",
                officer.getName()
        );

        assertEquals(
                "Director",
                officer.getRole()
        );

        assertEquals(
                LocalDate.of(2020, 5, 10),
                officer.getAppointedOn()
        );

        assertEquals(
                company,
                officer.getCompany()
        );

        assertEquals(
                1,
                company.getOfficers().size()
        );
    }
}
