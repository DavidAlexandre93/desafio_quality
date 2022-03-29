package br.com.meli.desafio_quality.dto;

import br.com.meli.desafio_quality.entity.DistrictEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import lombok.Data;

import java.util.List;


@Data
public class PropertyDTO {

    private String propName;
    private DistrictEntity district;
    private List<RoomEntity> rooms;


}
