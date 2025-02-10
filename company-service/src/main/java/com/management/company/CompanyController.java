package com.management.company;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.management.company.dto.CompanyDTO;
import com.management.company.entity.Company;
import com.management.company.exception.ResourceNotFoundException;
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
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    // Crear una nueva compañía
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    // Obtener una compañía por ID (excluyendo eliminadas)
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long companyId) {
        CompanyDTO company = companyService.getCompanyById(companyId);
        if (company == null || company.getIs_deleted()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }

    // Obtener todas las compañías (excluyendo eliminadas)
   @GetMapping
    public ResponseEntity<Page<CompanyDTO>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CompanyDTO> companies = companyService.getAllCompanies(pageable);

            // Filtrar compañías eliminadas
            List<CompanyDTO> filteredCompanies = companies.getContent()
                    .stream()
                    .filter(company -> !company.getIs_deleted()) // Excluir eliminadas
                    .collect(Collectors.toList());

            // Volver a crear un Page con la lista filtrada
            Page<CompanyDTO> finalPage = new PageImpl<>(filteredCompanies, pageable, filteredCompanies.size());

            return ResponseEntity.ok(finalPage);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Actualizar una compañía (verificar si no está eliminada)
  @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));

        company.setCompanyName(companyDetails.getCompanyName());
        company.setTaxId(companyDetails.getTaxId());
        company.setAddress(companyDetails.getAddress());
        company.setPhone(companyDetails.getPhone());
        company.setEmail(companyDetails.getEmail());
        company.setIsActive(companyDetails.getIsActive());
        company.setIsDeleted(companyDetails.getIsDeleted());

        Company updatedCompany = companyRepository.save(company);
        return ResponseEntity.ok(updatedCompany);
    }

    // Eliminar una compañía (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        Company existingCompany = companyRepository.findById(id).orElse(null);
        if (existingCompany == null || existingCompany.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        existingCompany.setIsDeleted(true);
        companyRepository.save(existingCompany);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hola Mundo");
    }

    // Obtener todas las compañías sin paginación (excluyendo eliminadas)
    @GetMapping("/test")
    public ResponseEntity<List<CompanyDTO>> getAllCompaniesWithoutPagination() {
        try {
            List<CompanyDTO> companies = companyRepository.findAll()
                    .stream()
                    .filter(company -> !company.getIsDeleted()) 
                    .map(companyMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(companies);
        } catch (Exception e) {
            throw e;
        }
    }
}
