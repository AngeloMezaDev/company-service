package com.management.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.management.company.dto.CompanyDTO;

import jakarta.validation.Valid;

@Validated
public interface CompanyService {
    CompanyDTO createCompany(@Valid CompanyDTO companyDTO);

    CompanyDTO getCompanyById(Long id);

    Page<CompanyDTO> getAllCompanies(Pageable pageable);

    CompanyDTO updateCompany(Long id, @Valid CompanyDTO companyDTO);

    void deleteCompany(Long id);
}