package com.management.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // Añadido para facilitar construcción de objetos
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class CompanyDTO {
    private Long companyId;

    @NotBlank(message = "El nombre de la compañía es obligatorio")
    @Size(max = 100, message = "El nombre de la compañía debe tener menos de 100 caracteres")
    private String companyName;

    @NotBlank(message = "El Tax ID es obligatorio")
    @Size(max = 20, message = "El Tax ID debe tener menos de 20 caracteres")
    private String taxId;

    @Size(max = 200, message = "La dirección debe tener menos de 200 caracteres")
    private String address;

    @Size(max = 20, message = "El teléfono debe tener menos de 20 caracteres")
    private String phone;

    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email debe tener menos de 100 caracteres")
    private String email;
}