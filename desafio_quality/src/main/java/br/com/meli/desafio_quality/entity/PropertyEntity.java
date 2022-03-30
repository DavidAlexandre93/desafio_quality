package br.com.meli.desafio_quality.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PropertyEntity {

    private static Integer idAcc = 0;

    private Integer id;
    private String propName;
    private DistrictEntity district;
    private List<RoomEntity> rooms;

    /**
     *
     * @param propName
     * @param district
     * @param rooms
     */
    public PropertyEntity(String propName, DistrictEntity district, List<RoomEntity> rooms) {
        this.propName = propName;
        this.district = district;
        this.rooms = rooms;
        idAcc += 1;
        this.id = idAcc;
    }
}
