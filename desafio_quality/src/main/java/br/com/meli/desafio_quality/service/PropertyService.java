package br.com.meli.desafio_quality.service;

import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class PropertyService {

    // REQUISITO 03
    @Autowired
    private PropertyRepository propertyRepository;

    public RoomEntity biggestRoom() {
        PropertyEntity propriedade = propertyRepository.getProperty();
        RoomEntity room = propriedade.getRooms().stream().max(Comparator.comparingDouble(RoomEntity::calculateArea)).get();
        propriedade.getRooms().forEach(r -> System.out.println(r.calculateArea()));
        System.out.println(room.calculateArea());
        return room;
    }
}
