package com.polixis.companysearch;

import com.polixis.companysearch.config.ScraperProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableConfigurationProperties(ScraperProperties.class)
public class CompanySearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanySearchApplication.class, args);
	}

}
