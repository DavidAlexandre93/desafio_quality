package br.com.meli.desafio_quality.controller;

import br.com.meli.desafio_quality.service.PropertyService;
import org.springframework.web.bind.annotation.*;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;

//@RequestMapping("/seuImovel")
@RestController
@NoArgsConstructor
public class Controller {

    PropertyService propertyService;

    @GetMapping(path = "/totalArea/{id}")
    public ResponseEntity<Double> totalPropertyArea(@PathVariable Integer id) {
        return ResponseEntity.ok(propertyService.totalPropertyArea(id));
    }


    @GetMapping("/totalpropriedade")
    public ResponseEntity<BigDecimal> precototalPropriedade(@RequestParam Integer id) {
        return ResponseEntity.ok(propertyService.calculatePrecoAreaTotal(id));
    }

}
