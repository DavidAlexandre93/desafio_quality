package br.com.meli.desafio_quality.controller;

import br.com.meli.desafio_quality.service.PropertyService;
import org.springframework.web.bind.annotation.*;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<Double> analisaValorPropriedade(@RequestParam Double valor) {
        return ResponseEntity.ok(propertyService.calculatePrecoAreaTotal());
    }

}
