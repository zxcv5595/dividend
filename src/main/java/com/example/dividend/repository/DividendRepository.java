package com.example.dividend.repository;

import com.example.dividend.domain.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<Dividend,Long> {
    List<Dividend> findByCompanyId(Long companyId);
}
