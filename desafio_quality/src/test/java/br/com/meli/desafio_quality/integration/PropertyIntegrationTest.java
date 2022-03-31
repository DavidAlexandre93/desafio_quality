package br.com.meli.desafio_quality.integration;

import br.com.meli.desafio_quality.dto.*;
import br.com.meli.desafio_quality.repository.PropertyRepository;
import br.com.meli.desafio_quality.unit.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PropertyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @BeforeEach
    public void setup() {
        propertyRepository.clear();
    }

    /**
     * @throws Exception
     */
    @Test
    @DisplayName("Test01 - US-0004 - Integração")
    public void calculateRoomsArea_shouldReturnSuccessResult_whenPropertyExistsOnDatabase() throws Exception {
        insertProperty();
        MvcResult mvcResult = mockMvc.perform(get("/property/roomarea/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        PropertyRoomsResponseDTO propertyRoomsResponseDTO = objectMapper.readValue(jsonResponse, PropertyRoomsResponseDTO.class);
        List<RoomAreaResponseDTO> rooms = propertyRoomsResponseDTO.getRooms();

        assertEquals("Casa", propertyRoomsResponseDTO.getPropName());
        assertThat(rooms)
                .extracting(RoomAreaResponseDTO::getArea)
                .containsExactlyInAnyOrder(35.0d, 180.0d);

        assertThat(rooms)
                .extracting(RoomAreaResponseDTO::getRoomName)
                .containsExactlyInAnyOrder("Banheiro", "Quarto");
    }

    /**
     * @throws Exception
     */
    @Test
    @DisplayName("Test02 - US-0004 - Integração")
    public void calculateRoomsArea_shouldReturnBadRequestResult_whenPropertyDoesNotExistsOnDatabase() throws Exception {
        mockMvc.perform(get("/property/roomarea/{id}", 1))
                .andExpect(status().isBadRequest());
    }


    private void insertProperty() throws Exception {
        String propertyJson = "{\n" +
                "    \"propName\": \"Casa\",\n" +
                "    \"district\": {\n" +
                "        \"propDistrict\": \"Em algum lugar\",\n" +
                "        \"valueDistrictM2\": 4.55\n" +
                "    },\n" +
                "    \"rooms\": [\n" +
                "        {\n" +
                "            \"roomName\" : \"Banheiro\",\n" +
                "            \"roomWidth\": 5.00,\n" +
                "            \"roomLength\": 7.00\n" +
                "        },\n" +
                "        {\n" +
                "            \"roomName\" : \"Quarto\",\n" +
                "            \"roomWidth\": 15.00,\n" +
                "            \"roomLength\": 12.00\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(post("/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(propertyJson));
    }


    /**
     * @throws Exception
     * @Author: David e Matheus
     * @Teste: Teste integrado
     * @Description: Validar o valor da propriedade e comparar com o resultado obtido
     */
    @Test
    public void valorPropriedade_shouldTrowNewError_whenInvalidValor() throws Exception {

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

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/totalpropriedade/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        BigDecimal jsonReturned = new BigDecimal(result.getResponse().getContentAsString());

        assertEquals(jsonReturned.round(new MathContext(2)), new BigDecimal(17875.00).round(new MathContext(2)));
    }

    @Test
    public void checkIfEndpointReturnsBiggestRoom() throws Exception {

////            Integer id = propertyRepository.findAll().stream().findFirst().get().getId();
////
//
//        String biggestRoom = propertyService.biggestRoom(1).getRoomName();
//
//            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
//                    .get("/biggestRoom/{id}"))
//                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//            String responseString = result.getResponse().getContentAsString();
//
//            assertEquals(responseString, "7.15");
//        }
//    }

    }
}