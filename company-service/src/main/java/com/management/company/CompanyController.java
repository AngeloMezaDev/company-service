package com.management.company;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.company.dto.CompanyDTO;
import com.management.company.mapper.CompanyMapper;
import com.management.company.repository.CompanyRepository;
import com.management.company.service.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private  final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    // Crear una nueva compañía
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    // Obtener una compañía por ID
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(
        @PathVariable(name = "companyId", required = true) final Long companyId
    ) {
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }
    
    @GetMapping
    public ResponseEntity<Page<CompanyDTO>> getAllCompanies(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CompanyDTO> companies = companyService.getAllCompanies(pageable);
            return ResponseEntity.ok(companies);
        } catch (Exception e) {
            throw e;
        }
    }

    // Actualizar una compañía
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    // Eliminar una compañía
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hola Mundo");
    }

    @GetMapping("/test")
    public ResponseEntity<List<CompanyDTO>> getAllCompaniesWithoutPagination() {
        try {
            return ResponseEntity.ok(
                companyRepository.findAll()
                    .stream()
                    .map(companyMapper::toDTO)
                    .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw e;
        }
    }
}
