
package com.polixis.companysearch.repository;

import com.polixis.companysearch.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyNumber(String companyNumber);
}
