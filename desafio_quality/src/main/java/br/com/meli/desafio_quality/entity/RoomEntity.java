package br.com.meli.desafio_quality.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {

    private String roomName;
    private Double roomWidth;
    private Double roomLength;

}
