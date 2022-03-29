package br.com.meli.desafio_quality.service;

import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.repository.PropertyRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PropertyService {
    private PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Double totalPropertyArea(Integer id) {
        List<RoomEntity> rooms = propertyRepository.findById(id).getRooms();

        AtomicReference<Double> totalArea = new AtomicReference<>(0.00);

        rooms.stream().forEach(roomEntity -> totalArea.updateAndGet(v -> v + roomEntity.getArea()));

        return totalArea.get();
    }
}
