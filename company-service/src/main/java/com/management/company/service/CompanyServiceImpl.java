package com.management.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.management.company.dto.CompanyDTO;
import com.management.company.entity.Company;
import com.management.company.exception.ResourceNotFoundException;
import com.management.company.mapper.CompanyMapper;
import com.management.company.repository.CompanyRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class CompanyServiceImpl implements CompanyService { // Corregido el nombre de la clase
    
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    public CompanyDTO createCompany(@Valid CompanyDTO companyDTO) {
        // Registro de intento de creación de compañía
        log.info("Intentando crear compañía con Tax ID: {}", companyDTO.getTaxId());

        // Verificación de existencia de compañía por Tax ID
        if (companyRepository.existsByTaxId(companyDTO.getTaxId())) {
            log.warn("Intento de crear compañía con Tax ID duplicado: {}", companyDTO.getTaxId());
            throw new IllegalArgumentException("Una compañía con este Tax ID ya existe");
        }

        // Conversión de DTO a entidad, guardado y conversión de vuelta a DTO
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        
        log.info("Compañía creada exitosamente con ID: {}", company.getCompanyId());
        return companyMapper.toDTO(company);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDTO getCompanyById(Long id) {
        // Búsqueda de compañía por ID con manejo de errores
        log.debug("Buscando compañía con ID: {}", id);
        Company company = findCompanyById(id);
        return companyMapper.toDTO(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> getAllCompanies(Pageable pageable) {
        // Obtención de todas las compañías con paginación
        log.debug("Obteniendo lista de compañías. Página: {}", pageable.getPageNumber());
        return companyRepository.findAll(pageable)
                .map(companyMapper::toDTO);
    }

    @Override
    @Transactional
    public CompanyDTO updateCompany(Long id, @Valid CompanyDTO companyDTO) {
        // Búsqueda de compañía existente
        log.info("Iniciando actualización de compañía con ID: {}", id);
        Company existingCompany = findCompanyById(id);
        
        // Actualización selectiva de campos
        updateCompanyFields(existingCompany, companyDTO);

        // Guardado y conversión a DTO
        existingCompany = companyRepository.save(existingCompany);
        log.info("Compañía actualizada exitosamente: {}", id);
        return companyMapper.toDTO(existingCompany);
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        // Eliminación lógica de compañía
        log.info("Iniciando eliminación lógica de compañía con ID: {}", id);
        Company company = findCompanyById(id);
        company.setIsActive(false);
        companyRepository.save(company);
        log.info("Compañía desactivada: {}", id);
    }

    // Método privado para buscar compañía por ID
    private Company findCompanyById(Long id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Compañía no encontrada con ID: {}", id);
                return new ResourceNotFoundException("Compañía no encontrada con ID: " + id);
            });
    }

    // Método privado para actualización selectiva de campos
    private void updateCompanyFields(Company existingCompany, CompanyDTO companyDTO) {
        // Actualización de campos solo si no son nulos
        if (companyDTO.getCompanyName() != null) {
            existingCompany.setCompanyName(companyDTO.getCompanyName());
        }
        if (companyDTO.getTaxId() != null) {
            existingCompany.setTaxId(companyDTO.getTaxId());
        }
        if (companyDTO.getAddress() != null) {
            existingCompany.setAddress(companyDTO.getAddress());
        }
        if (companyDTO.getPhone() != null) {
            existingCompany.setPhone(companyDTO.getPhone());
        }
        if (companyDTO.getEmail() != null) {
            existingCompany.setEmail(companyDTO.getEmail());
        }
    }
}