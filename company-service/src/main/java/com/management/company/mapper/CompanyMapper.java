package com.management.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.management.company.dto.CompanyDTO;
import com.management.company.entity.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(source = "companyId", target = "companyId")
    Company toEntity(CompanyDTO dto);

    @Mapping(source = "companyId", target = "companyId")
    CompanyDTO toDTO(Company entity);
}