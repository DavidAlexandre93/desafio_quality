package br.com.meli.desafio_quality.service;

import br.com.meli.desafio_quality.dto.PropertyDTO;
import br.com.meli.desafio_quality.entity.DistrictEntity;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.repository.DistrictRepository;
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
    private DistrictRepository districtRepository = new DistrictRepository();


    /**
     *
     * @param propertyRepository
     */
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }


    /**
     * @Author Bruno
     * @Description Criar uma nova propriedade
     * @param input
     * @return a propriedade cadstrada
     */
    public PropertyEntity addProperty(PropertyDTO input) {
        PropertyEntity property = new PropertyEntity(input.getPropName(),
                new DistrictEntity(input.getDistrict().getPropDistrict(), input.getDistrict().getValueDistrictM2()),
                input.getRooms().stream().map(r -> new RoomEntity(r.getRoomName(), r.getRoomWidth(), r.getRoomLength())).collect(Collectors.toList())
        );
        propertyRepository.create(property);
        return property;
    }

    /**
     * @Author Bruno e Pedro
     * @Description Busca a propriedade no repository e calcula a area total desa propriedade
     * @param id
     * @return A área total da propriedade
     */
    public Double totalPropertyArea(Integer id) {
        List<RoomEntity> rooms = propertyRepository.findById(id).getRooms();

        AtomicReference<Double> totalArea = new AtomicReference<>(0.00);

        rooms.forEach(roomEntity -> totalArea.updateAndGet(v -> v + roomEntity.getArea()));

        return totalArea.get();
    }

    /**
     * @Author: David e Matheus
     * @Metodo: Validar se o Bairro Existe ou Nao
     * @Description: Realizar a validacao da existencia do bairro antes do calculo ser realizado
     * @param id
     * @return
     */
    public boolean bairroExiste(Integer id) {
        try {
            propertyRepository.findById(id).getDistrict().getPropDistrict();
            return true;
        }catch (NullPointerException e){
            return false;
        }
    }

    /**
     * Author: David e Matheus
     * @Metodo: calcular o preco total da propriedade com base no m2
     * @Description: Realizar o calculo de cada propriedade informada de acorodo com o m2 e seus comodos
     * @param id
     * @return
     * @throws IllegalArgumentException
     */
   public BigDecimal calculatePrecoAreaTotal(Integer id) throws IllegalArgumentException {

        PropertyEntity property = propertyRepository.findById(id);
   //  if(!bairroExiste(id)) {

     //    throw new IllegalArgumentException("Bairro não encontrado no banco de dados");
    // }
     //else {
         BigDecimal totalproperty = new BigDecimal(totalPropertyArea(id));
         return totalproperty.multiply(property.getDistrict().getValueDistrictM2());
  //   }
   }

    /**
     *
     * @param id
     * @return
     */
    public RoomEntity biggestRoom(Integer id) {
        PropertyEntity property = propertyRepository.findById(id);
        RoomEntity room = property.getRooms().stream().max(Comparator.comparingDouble(RoomEntity::calculateArea)).get();

        return room;
    }
    /**  Dado o id de um imóvel, busca e calcula a área de cada um de seus cômodos.
     *
     * @param  propertyId id do imóvel alvo da operação.
     * @return Entidade do imóvel com cada um de seus cômodos com a área calculada.
     */
    public PropertyEntity calculateRoomsArea(Integer propertyId) {
        PropertyEntity property = propertyRepository.findById(propertyId);
        property.getRooms().forEach(room -> room.setArea(calculateRoomEntityArea(room)));
        return property;
    }

    /**
     * @Metodo: Dado um cômodo calcula sua área seguindo a fórmula largura x comprimento.
     * @Description:
     * @param  roomEntity Cômodo em que se deseja calcular a área.
     * @return A área do cômodo recebido.
     */
    private double calculateRoomEntityArea(RoomEntity roomEntity) {
        return roomEntity.getRoomWidth() * roomEntity.getRoomLength();
    }

}
