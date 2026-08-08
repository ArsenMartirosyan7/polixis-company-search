# Company Data Search Service

A small Spring Boot service that searches UK companies through the Companies House website and returns company information as JSON.

The service collects basic company details and officer information, stores the results in PostgreSQL, and reuses previously fetched results while they are still fresh.

## Running the project

### Requirements

* Java 21
* Docker
* Docker Compose

### Start PostgreSQL

From the project directory:

```bash
docker compose up -d
```

The PostgreSQL container is exposed on port `5433` and creates the `company_search` database automatically.

### Start the application

```bash
./mvnw spring-boot:run
```

The application will be available at:

```text
http://localhost:8080
```

Tests can be run with:

```bash
./mvnw test
```

## Search endpoint

```http
GET /api/companies/search?query={query}
```

Example:

```http
GET /api/companies/search?query=00445790
```

The response contains the search query, cache information, fetch time, number of matching companies, company details and their officers.

Example response:

```json
{
  "query": "00445790",
  "cached": false,
  "fetchedAt": "2026-08-09T00:15:20",
  "count": 1,
  "companies": [
    {
      "companyNumber": "00445790",
      "name": "TESCO PLC",
      "status": "Active",
      "companyType": "Public limited Company",
      "incorporatedOn": "1947-11-27",
      "dissolvedOn": null,
      "registeredAddress": "Tesco House, Shire Park, Kestrel Way, Welwyn Garden City, AL7 1GA",
      "officers": [
        {
          "name": "OFFICER NAME",
          "role": "Director",
          "appointedOn": "2020-01-01",
          "resignedOn": null
        }
      ]
    }
  ]
}
```

A cached result can be bypassed with:

```http
GET /api/companies/search?query=00445790&forceRefresh=true
```

## Caching

Searches are stored in PostgreSQL together with the time they were fetched. If the same normalized query is requested again within 24 hours, the stored result is returned instead of requesting Companies House again. After 24 hours, the data is fetched again and the stored result is updated. The `forceRefresh` parameter can also be used to refresh the data manually.

## Hardest part

The main challenge was scraping data from several Companies House pages and combining it into one company model without making the scraping code too dependent on the page layout. I separated fetching and parsing into different classes so that the parsing logic could be tested independently using local HTML.

Another consideration was caching. Keeping results forever would avoid unnecessary requests, but company information can change, so I used a simple 24-hour expiration period.

## What I would improve

I did not implement persons with significant control because I focused first on completing and testing the required company and officer flow.

With more time, I would add PSC information, better handling of temporary Companies House failures with retry/backoff logic, and pagination for searches with larger result sets.
