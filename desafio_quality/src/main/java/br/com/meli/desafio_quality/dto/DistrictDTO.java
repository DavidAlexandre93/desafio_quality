package br.com.meli.desafio_quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDTO {

    @Size(max = 45, message = "O comprimento não pode exceder 45 caracteres.")
    @NotBlank(message = "O bairro não pode estar vazio")
    private String propDistrict;

    @NotNull(message = "O valor do metro quadrado no bairro não pode estar vazio.")
    @Digits(integer = 13, fraction = 2, message = "O valor deve conter no máximo 13 digitos")
    private BigDecimal valueDistrictM2;
}
