package br.com.meli.desafio_quality.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRoomsResponseDTO {
    private String propName;
    List<RoomAreaResponseDTO> rooms;
}
