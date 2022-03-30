package br.com.meli.desafio_quality.service;



import br.com.meli.desafio_quality.dto.DistrictDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.databind.ObjectMapper;
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


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyEndPointTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropertyService propertyService;

    public void classTest() throws Exception{

       mvc.perform(MockMvcRequestBuilders.post("/teste")
               .contentType(MediaType.APPLICATION_JSON)
               .content(""))
               .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void valorPropriedade_shouldTrowNewError_whenInvalidValor(Integer id) throws Exception {

        BigDecimal valorProperty = propertyService.calculatePrecoAreaTotal(id);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/totalpropriedade", valorProperty.add(BigDecimal.valueOf(id)))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String jsonReturned = result.getResponse().getContentAsString();
        DistrictDTO dto = new ObjectMapper().readValue(jsonReturned, DistrictDTO.class);
        assertEquals(propertyService.calculatePrecoAreaTotal(id), dto.getValueDistrictM2());
    }




}
