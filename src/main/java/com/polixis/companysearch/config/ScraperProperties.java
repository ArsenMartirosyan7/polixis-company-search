package com.polixis.companysearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scraper")
public class ScraperProperties {

    private String baseUrl;
    private String userAgent;
    private long delayMs;
    private int maxCompanies;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public long getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(long delayMs) {
        this.delayMs = delayMs;
    }

    public int getMaxCompanies() {
        return maxCompanies;
    }

    public void setMaxCompanies(int maxCompanies) {
        this.maxCompanies = maxCompanies;
    }
}
