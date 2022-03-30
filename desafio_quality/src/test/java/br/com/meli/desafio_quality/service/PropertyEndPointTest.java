package br.com.meli.desafio_quality.service;


import br.com.meli.desafio_quality.repository.PropertyRepository;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyEndPointTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private PropertyRepository propertyRepository;


    public void valorTotalPropriedade_shouldReturnTotalPropertyValor_whenByPropertyId(){

        ResponseEntity<String> forEntity = testRestTemplate.getForEntity("/totalpropriedade/", String.class);


    }
}
