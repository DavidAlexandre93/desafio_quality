package br.com.meli.desafio_quality.controller;

import br.com.meli.desafio_quality.dto.PropertyDTO;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.repository.PropertyRepository;
import br.com.meli.desafio_quality.service.PropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;

//@RequestMapping("/seuImovel")
@RestController
public class Controller {

    PropertyRepository propertyRepository;
    PropertyService propertyService;

    public Controller() {
        propertyRepository = new PropertyRepository();
        propertyService = new PropertyService(propertyRepository);
    }

    @PostMapping("/new")
    public ResponseEntity<PropertyEntity> createProperty(@Valid @RequestBody PropertyDTO input) {
        PropertyEntity property = propertyService.addProperty(input);
        return ResponseEntity.ok(property);
    }

    @GetMapping(path = "/totalArea/{id}")
    public ResponseEntity<Double> totalPropertyArea(@PathVariable Integer id) {
        return ResponseEntity.ok(propertyService.totalPropertyArea(id));
    }


    @GetMapping("/totalpropriedade")
    public ResponseEntity<BigDecimal> precototalPropriedade(@RequestParam Integer id) {
        return ResponseEntity.ok(propertyService.calculatePrecoAreaTotal(id));
    }

}
