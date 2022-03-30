package br.com.meli.desafio_quality.integration;

import br.com.meli.desafio_quality.dto.PropertyRoomsResponseDTO;
import br.com.meli.desafio_quality.dto.RoomAreaResponseDTO;
import br.com.meli.desafio_quality.repository.PropertyRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void setup() {
        propertyRepository.clear();
    }

    @Test
    @DisplayName("Test01 - US-0004 - Integração")
    public void calculateRoomsArea_shouldReturnSuccessResult_whenPropertyExistsOnDatabase() throws Exception {
        insertProperty();
        MvcResult mvcResult = mockMvc.perform(get("/property/{id}/rooms-area", 1))
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

    @Test
    @DisplayName("Test02 - US-0004 - Integração")
    public void calculateRoomsArea_shouldReturnBadRequestResult_whenPropertyDoesNotExistsOnDatabase() throws Exception {
        mockMvc.perform(get("/property/{id}/rooms-area", 1))
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
}
