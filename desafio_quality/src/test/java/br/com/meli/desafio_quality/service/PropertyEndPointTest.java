package br.com.meli.desafio_quality.service;



import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyEndPointTest {

    @Autowired
    private MockMvc mvc;

    public void valorTotalPropriedade_shouldReturnTotalPropertyValor_whenByPropertyId() throws Exception{

       mvc.perform(MockMvcRequestBuilders.post("/teste").contentType(MediaType.APPLICATION_JSON)
               .content("")
       ).andExpect(MockMvcResultMatchers.status().isCreated());


    }
}
