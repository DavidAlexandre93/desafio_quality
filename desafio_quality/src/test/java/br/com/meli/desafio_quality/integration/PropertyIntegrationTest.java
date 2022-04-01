package br.com.meli.desafio_quality.integration;

import br.com.meli.desafio_quality.dto.*;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.MathContext;
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

    @BeforeEach
    public void setup() {
            propertyRepository.clear();
    }

    /**
     *
     * @throws Exception
     */
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
     * @Author: Maik
     * @Description: Valida se o cálculo da área dos cômodos é efetuado de forma correta;
     * Refere-se ao requisito US-0004
     * @throws Exception
     */
    @Test
    @DisplayName("Test01 - US-0004 - Integração")
    public void calculateRoomsArea_shouldReturnSuccessResult_whenPropertyExistsOnDatabase() throws Exception {
        insertProperty();
        MvcResult mvcResult = mockMvc.perform(get("/property/{id}/rooms-area", 0))
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
     * @Author: Maik
     * @Description: Valida se o caso em que a propriedade não existe no banco de dados, então espera-se o retorno como
     * bad request;
     * Refere-se ao requisito US-0004
     * @throws Exception
     */
    @Test
    @DisplayName("Test02 - US-0004 - Integração")
    public void calculateRoomsArea_shouldReturnBadRequestResult_whenPropertyDoesNotExistsOnDatabase() throws Exception {
        mockMvc.perform(get("/property/{id}/rooms-area", 0))
                .andExpect(status().isBadRequest());
    }

    /**
     * @Author: David e Matheus
     * @Teste: Teste integrado
     * @Description: Validar o valor da propriedade e comparar com o resultado obtido
     * @throws Exception
     */
    @Test
    @DisplayName("Test01 - US-0002 - Integração")
    public void valorPropriedade_shouldTrowNewError_whenInvalidValor() throws Exception {

        insertProperty();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/totalpropriedade/{id}",0))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        BigDecimal jsonReturned = new BigDecimal(result.getResponse().getContentAsString());

        assertEquals(jsonReturned.round(new MathContext(2)), new BigDecimal(978.25).round(new MathContext(2)));
    }

    /**
     * @Author: Bruno
     * @Teste: Teste integrado req 001
     * @Description: Validar se o endpoint retorna a area correta da propriedade
     * @throws Exception
     */
    @Test
    public void checkIfEndpointReturnsCorrectAnswer() throws Exception{
        insertProperty();
        Integer id = propertyRepository.findAll().stream().findFirst().get().getId();
        String url = "/totalArea/" + id.toString();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(url))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseString = result.getResponse().getContentAsString();

        assertEquals(responseString, "215.0");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void checkIfEndpointReturnsErrorWhenReceiveingWringInput() throws Exception{
        insertProperty();
        Integer id = propertyRepository.findAll().stream().findFirst().get().getId() + 1;
        String url = "/totalArea/" + id.toString();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(url))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        String responseString = result.getResponse().getErrorMessage();

        assertEquals(responseString, "Propriedade não encontrada");
    }
    /**
     * @Author: Mariana e Micaela
     * @Teste: Teste integrado req 003
     * @Description: Validar se o endpoint retorna o maior comodo da propriedade buscada pelo id
     * @throws Exception
     */
    @Test
    @DisplayName("Test01 - US-0003 - Integração")
    public void biggestRoom_shouldBiggestRoom_whenValidId() throws Exception {
        insertProperty();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/biggestRoom/{id}", 0))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();

        assertEquals("{\"roomName\":\"Quarto\",\"roomWidth\":15.0,\"roomLength\":12.0,\"area\":180.0}", resp);
    }
    /**
     * @Author: Mariana e Micaela
     * @Teste: Teste integrado req 003
     * @Description: Validar se o endpoint retorna erro ao indicar Id invalido
     * @throws Exception
     */
    @Test
    @DisplayName("Test02 - US-0003 - Integração")
    public void biggestRoom_shouldBiggestRoom_whenInvalidId() throws Exception {
        insertProperty();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/biggestRoom/{id}", 99))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        String resp = result.getResponse().getErrorMessage();

        assertEquals("Propriedade não encontrada", resp);
    }

}
