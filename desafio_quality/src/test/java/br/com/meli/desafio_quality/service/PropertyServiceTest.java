package br.com.meli.desafio_quality.service;


import br.com.meli.desafio_quality.entity.DistrictEntity;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.repository.PropertyRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyServiceTest {

    private PropertyRepository propertyRepository;
    private PropertyService propertyService;
    private DistrictEntity districtOne = new DistrictEntity("districtOne", new BigDecimal(2500));
    private RoomEntity kitchen = new RoomEntity("Kitchen", 2.50, 1.5);
    private RoomEntity bedRoom = new RoomEntity("bedRoom", 1.20, 2.0);
    private RoomEntity bathRoom = new RoomEntity("bathRoom", 1.0, 1.0);
    private PropertyEntity propertyHouse;


    /**
     *
     */
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
     *
     */
    @Test
    @DisplayName("Test01 - US-0001")
    public void totalPropertyArea_shouldReturnTotalPropertyArea_whenByPropertyId() {
        when(propertyRepository.findById(1)).thenReturn(propertyHouse);
        assert propertyService.totalPropertyArea(1).equals(7.15);
    }

    /**
     *
     */
    @Test
    @DisplayName("Test02 - US-0001")
    public void totalPropertyArea_shouldTrowNewError_whenInvalidId() {
        when(propertyRepository.findById(2)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));

        assertThrows(ResponseStatusException.class, () -> propertyService.totalPropertyArea(2));
    }

    /**
     * @Author: Mariana e Micaela
     * @Teste: Teste integrado req 003
     * @Description: Validar se o endpoint retorna o maior comodo da propriedade buscada pelo id
     * @throws Exception
     */
    @Test
    @DisplayName("Test01 - US-0003")
    public void biggestRoom_shouldBiggestRoom_whenValidId(){
        Mockito.when(propertyRepository.findById(1)).thenReturn(propertyHouse);
        assert(propertyService.biggestRoom(1)).getRoomName().equals("Kitchen");
    }

    /**
     * @Author: Mariana e Micaela
     * @Teste: Teste unitario req 003
     * @Description: Validar se o endpoint retorna erro ao indicar Id invalido
     * @throws Exception
     */
    @Test
    @DisplayName("Test02 - US-0003")
    public void biggestRoom_shouldBiggestRoom_whenInvalidId() {
        Mockito.when(propertyRepository.findById(2)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));
        assertThrows(ResponseStatusException.class, () -> propertyService.biggestRoom(2));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test01 - US-0004")
    public void calculateRoomsArea_shouldProperlyCalculateEachRoomArea_whenPropertyIsFound() {
        when(propertyRepository.findById(any(Integer.class))).thenReturn(propertyHouse);

        PropertyEntity returnedProperty = propertyService.calculateRoomsArea(1);

        List<Double> roomsArea = returnedProperty.getRooms().stream()
                .map(RoomEntity::getArea)
                .collect(Collectors.toList());

        assertThat(roomsArea).containsExactlyInAnyOrder(3.75d, 2.4d , 1d);
    }

    /**
     *
     */
    @Test
    @DisplayName("Test02 - US-0004")
    public void calculateRoomsArea_shouldThrowResponseStatusException_whenPropertyDoesNotExists() {
        when(propertyRepository.findById(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));
        assertThrows(ResponseStatusException.class, () -> propertyService.calculateRoomsArea(2));
    }


    /**
     *
     */
    @Test
    @DisplayName("Test01 - US-0002")
    public void totalValor_shouldTrowNewError_whenInvalidId() {

        Mockito.when(propertyRepository.findById(2)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));

        assertThrows(ResponseStatusException.class, () -> propertyService.totalPropertyArea(2));
    }

    /**
     * @Descritption: Verifique se o bairro de entrada existe no
     * repositório de bairro
     */
    @Test
    @DisplayName("Test01 - US-0002")
    public void bairroExiste_shouldTrowNewError_whenInvalidBairro() {

       // Throwable exception = assertThrows(RuntimeException.class,()->propertyService.bairroExiste(2));
        //assertEquals("Bairro nao existe!", exception.getMessage());

        Mockito.when(propertyRepository.findById(1)).thenReturn(propertyHouse);
        assert(propertyService.bairroExiste(1));

    }

    /**
     * @Descritption: Verificar se o valor da propriedade com base no comodo e medida se esta correto
     */
    @Test
    @DisplayName("Test02 - US-0002")
    public void valorPropriedade_shouldTrowNewError_whenInvalidValor() {

        Mockito.when(propertyRepository.findById(2)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));
        assertThrows(ResponseStatusException.class, () -> propertyService.calculatePrecoAreaTotal(2));

    }


    /**
     * @Descritption: Verificar se a funcao de calculo do valor total de uma propriedade retorna o valor correto de calculo
     */
    @Test
    @DisplayName("Test03 - US-0002")
    public void calculatePrecoAreaTotal_shouldReturnValorPropriedade_whenByPropertyId() {
        boolean result = false;
        when(propertyRepository.findById(1)).thenReturn(propertyHouse);

        if(propertyService.calculatePrecoAreaTotal(1).compareTo(BigDecimal.valueOf(17875.00)) == 0)
            result = true;
        else
            result = false;

        assert result;

    }


}
