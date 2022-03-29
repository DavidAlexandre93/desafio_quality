package br.com.meli.desafio_quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    @Size(max = 30, message = "O comprimento do nome do cômodo não deve exceder 30 caracteres.")
    @NotBlank(message = "O campo não pode estar vazio")
    @Pattern(regexp = "[A-Z].*", message = "O nome da cômodo deve começar com uma letra maiúscula.")
    private String roomName;

    @NotNull(message = "A largura do cômodo não pode estar vazia.")
    @Range(max = 25, message = "A largura máxima do cômodo deve ser de no máximo 25 metros.")
    private Double roomWidth;

    @NotNull(message = "O comprimento do cômodo não pode estar vazia.")
    @Range(max = 33, message = "O comprimento máximo do cômodo deve ser de no máximo 33 metros.")
    private Double roomLength;
}
