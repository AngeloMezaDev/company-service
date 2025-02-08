package com.management.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.company.entity.Company;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByTaxId(String taxId);
}