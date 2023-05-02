package com.example.dividend.repository;

import com.example.dividend.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByTicker(String ticker);
    Optional<Company> findByName(String name);
}
