package com.management.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.management.company.dto.CompanyDTO;
import com.management.company.entity.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "companyId", target = "companyId")
    @Mapping(source = "is_active", target = "isActive")
    @Mapping(source = "is_deleted", target = "isDeleted")
    Company toEntity(CompanyDTO dto);

    @Mapping(source = "companyId", target = "companyId")
    @Mapping(source = "isActive", target = "is_active")
    @Mapping(source = "isDeleted", target = "is_deleted")
    CompanyDTO toDTO(Company entity);
}