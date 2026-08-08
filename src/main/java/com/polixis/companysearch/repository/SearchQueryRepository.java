package com.polixis.companysearch.repository;

import com.polixis.companysearch.entity.SearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchQueryRepository extends JpaRepository<SearchQuery, Long> {

    Optional<SearchQuery> findByQuery(String query);
}
