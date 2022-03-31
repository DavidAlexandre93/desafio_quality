package br.com.meli.desafio_quality.unit.service;



import br.com.meli.desafio_quality.dto.DistrictDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import br.com.meli.desafio_quality.dto.PropertyDTO;
import br.com.meli.desafio_quality.dto.RoomDTO;
import br.com.meli.desafio_quality.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyEndPointTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void beforeEach() {
        propertyRepository.clear();
        DistrictDTO districtOne = new DistrictDTO("districtOne", new BigDecimal(2500));
        RoomDTO kitchen = new RoomDTO("Kitchen", 2.50, 1.5);
        RoomDTO bedRoom = new RoomDTO("bedRoom", 1.20, 2.0);
        RoomDTO bathRoom = new RoomDTO("bathRoom", 1.0, 1.0);

        List<RoomDTO> roomDto = new ArrayList<>();

        roomDto.add(kitchen);
        roomDto.add(bathRoom);
        roomDto.add(bedRoom);

        PropertyDTO property = new PropertyDTO("House", districtOne, roomDto);
        propertyService.addProperty(property);
    }

    /**
     * @Author: David e Matheus
     * @Teste: Teste integrado
     * @Description: Validar o valor da propriedade e comparar com o resultado obtido
     * @throws Exception
     */
    @Test
    public void valorPropriedade_shouldTrowNewError_whenInvalidValor() throws Exception {
        Integer id = propertyRepository.findAll().stream().findFirst().get().getId();
        String url = "/totalpropriedade/" + id.toString();
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(url))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
              BigDecimal jsonReturned = new BigDecimal(result.getResponse().getContentAsString());

              assertEquals(jsonReturned.round(new MathContext(2)), new BigDecimal(17875.00).round(new MathContext(2)));
    }


    /**
     * @Author: Bruno
     * @Teste: Teste integrado req 001
     * @Description: Validar se o endpoint retorna a area correta da propriedade
     * @throws Exception
     */
    @Test
    public void checkIfEndpointReturnsCorrectAnswer() throws Exception{
        Integer id = propertyRepository.findAll().stream().findFirst().get().getId();
        String url = "/totalArea/" + id.toString();
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(url))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseString = result.getResponse().getContentAsString();

        assertEquals(responseString, "7.15");
    }

    @Test
    public void checkIfEndpointReturnsErrorWhenReceiveingWringInput() throws Exception{
        Integer id = propertyRepository.findAll().stream().findFirst().get().getId() + 1;
        String url = "/totalArea/" + id.toString();
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(url))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        String responseString = result.getResponse().getErrorMessage();

        assertEquals(responseString, "Propriedade não encontrada");
    }
}
