package br.com.meli.desafio_quality.controller;

<<<<<<< HEAD
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
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
>>>>>>> 6c41c021c460348bff553359313f481be3c4e025

//@RequestMapping("/seuImovel")
@RestController
public class Controller {
    @Autowired
    private PropertyService propertyService;

<<<<<<< HEAD
    @GetMapping("/biggestRoom")
    public RoomEntity getBiggestRoom() {
        RoomEntity rooms = propertyService.biggestRoom();
        return rooms;

=======
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
>>>>>>> 6c41c021c460348bff553359313f481be3c4e025
    }

}
