package br.com.meli.desafio_quality.service;


import br.com.meli.desafio_quality.entity.DistrictEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.repository.PropertyRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    /**
     * Indicar o preço dessa mesma propriedade com base na área total.
     * Lembre-se que os preços por metro quadrado são determinados em função do
     * bairro.
     */
    //totalArea * valueDistrictM2
   public Double calculatePrecoAreaTotal(Double newResult) {
      DistrictEntity district = new DistrictEntity();
       try {
           newResult = Math.multiplyExact(district.getValueDistrictM2(),totalArea);
           return newResult;
       } catch (ArithmeticException a) {
           a.printStackTrace();
       }
       //return district.getValueDistrictM2().multiply(BigDecimal.valueOf(this.totalPropertyArea());

       return newResult;
    }
}
