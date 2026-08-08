package com.polixis.companysearch.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "search_queries")
public class SearchQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String query;

    @Column(nullable = false)
    private LocalDateTime fetchedAt;

    @ManyToMany
    @JoinTable(
            name = "search_query_companies",
            joinColumns = @JoinColumn(name = "search_query_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companies = new ArrayList<>();

    public SearchQuery() {
    }

    public Long getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public LocalDateTime getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(LocalDateTime fetchedAt) {
        this.fetchedAt = fetchedAt;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
