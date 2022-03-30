package br.com.meli.desafio_quality.service;


import br.com.meli.desafio_quality.entity.DistrictEntity;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.repository.PropertyRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PropertyServiceTest {

    private PropertyRepository propertyRepository;
    private PropertyService propertyService;
    private DistrictEntity districtOne = new DistrictEntity("districtOne", new BigDecimal(2500));
    private RoomEntity kitchen = new RoomEntity("Kitchen", 2.50, 1.3);
    private RoomEntity bedRoom = new RoomEntity("bedRoom", 1.20, 2.15);
    private RoomEntity bathRoom = new RoomEntity("bathRoom", 1.0, 1.0);
    private PropertyEntity propertyHouse;



    @BeforeEach
    public void beforeEach() {
        propertyRepository = Mockito.mock(PropertyRepository.class);
        propertyService = new PropertyService(propertyRepository);
        List<RoomEntity> roomEntities = new ArrayList<>();

        roomEntities.add(kitchen);
        roomEntities.add(bathRoom);
        roomEntities.add(bedRoom);

        propertyHouse = new PropertyEntity("House", districtOne, roomEntities);
    }

    /**
     * @Description: Padrao de metodo para os demais testes
     */
    @Test
    @DisplayName("Test - US-0001")
    public void nomeMetodo_shouldCOMPORTOMENT_whenCONDICAO() {

    }

    @Test
    @DisplayName("Test01 - US-0001")
    public void totalPropertyArea_shouldReturnTotalPropertyArea_whenByPropertyId() {
        Mockito.when(propertyRepository.findById(1)).thenReturn(propertyHouse);
        assert propertyService.totalPropertyArea(1).equals(6.83);
    }

    @Test
    @DisplayName("Test02 - US-0001")
    public void totalPropertyArea_shouldTrowNewError_whenInvalidId() {
        Mockito.when(propertyRepository.findById(2)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));

        assertThrows(ResponseStatusException.class, () -> propertyService.totalPropertyArea(2));
    }

    @Test
    @DisplayName("Test01 - US-0003")
    public void biggestRoom_shouldBiggestRoom_whenValidId(){
        Mockito.when(propertyRepository.findById(1)).thenReturn(propertyHouse);
        assert(propertyService.biggestRoom(1)).getRoomName().equals("Kitchen");
    }
}
