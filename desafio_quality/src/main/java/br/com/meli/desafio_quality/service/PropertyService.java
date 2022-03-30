package br.com.meli.desafio_quality.service;

<<<<<<< HEAD
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
=======
import br.com.meli.desafio_quality.dto.PropertyDTO;
import br.com.meli.desafio_quality.entity.DistrictEntity;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.repository.PropertyRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

>>>>>>> 6c41c021c460348bff553359313f481be3c4e025
    }
}
