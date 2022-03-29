package br.com.meli.desafio_quality.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RoomEntity {

    private String roomName;
    private Double roomWidth;
    private Double roomLength;
    private Double area;

    public RoomEntity(String roomName, Double roomWidth, Double roomLength) {
        this.roomName = roomName;
        this.roomWidth = roomWidth;
        this.roomLength = roomLength;
        this.area = calculateArea();
    }

    private Double calculateArea() {
        return this.roomLength * this.roomWidth;
    }

}
