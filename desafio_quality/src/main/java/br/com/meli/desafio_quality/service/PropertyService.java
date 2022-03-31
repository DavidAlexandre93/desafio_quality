package br.com.meli.desafio_quality.service;

import br.com.meli.desafio_quality.dto.PropertyDTO;
import br.com.meli.desafio_quality.entity.DistrictEntity;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.repository.PropertyRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    private PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public PropertyEntity addProperty(PropertyDTO input) {
        PropertyEntity property = new PropertyEntity(input.getPropName(),
                new DistrictEntity(input.getDistrict().getPropDistrict(), input.getDistrict().getValueDistrictM2()),
                input.getRooms().stream().map(r -> new RoomEntity(r.getRoomName(), r.getRoomWidth(), r.getRoomLength())).collect(Collectors.toList())
        );
        propertyRepository.create(property);
        return property;
    }

    public Double totalPropertyArea(Integer id) {
        List<RoomEntity> rooms = propertyRepository.findById(id).getRooms();

        AtomicReference<Double> totalArea = new AtomicReference<>(0.00);

        rooms.stream().forEach(roomEntity -> totalArea.updateAndGet(v -> v + roomEntity.getArea()));

        return totalArea.get();
    }

    /**
     * Indicar o preço dessa mesma propriedade com base na área total.
     * Lembre-se que os preços por metro quadrado são determinados em função do
     * bairro.
     */
   public BigDecimal calculatePrecoAreaTotal(Integer id) {

       PropertyEntity property = propertyRepository.findById(id);
       BigDecimal proprieArea = new BigDecimal(totalPropertyArea(id));
       return proprieArea.multiply(property.getDistrict().getValueDistrictM2());

    }

    // Req 03

    public RoomEntity biggestRoom(Integer id) {
        PropertyEntity property = propertyRepository.findById(id);
        RoomEntity room = property.getRooms().stream().max(Comparator.comparingDouble(RoomEntity::calculateArea)).get();
        return room;
    }
}
