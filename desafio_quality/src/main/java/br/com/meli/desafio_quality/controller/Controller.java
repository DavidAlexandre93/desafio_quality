package br.com.meli.desafio_quality.controller;

import br.com.meli.desafio_quality.dto.PropertyDTO;
import br.com.meli.desafio_quality.dto.PropertyRoomsResponseDTO;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.unit.service.PropertyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import br.com.meli.desafio_quality.repository.DistrictRepository;
import br.com.meli.desafio_quality.repository.PropertyRepository;
import br.com.meli.desafio_quality.service.PropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class Controller {


    private final PropertyService propertyService;
    private final ModelMapper modelMapper;

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

    @GetMapping("/biggestRoom/{id}")
    public RoomEntity getBiggestRoom(@PathVariable Integer id) {
        RoomEntity room = propertyService.biggestRoom(id);
        return room;
    }
    /**
     * Busca um imóvel a partir do id fornecido e caso exista retorna a lista de seus cômodos com as respectivas áreas
     * calculadas, caso o imóvel não seja encontrado na base de dados resume-se em um retorno com status bad request.
     *
     * @param   id  id do imóvel
     * @return      lista de cômodos do imóvel com suas áreas calculadas
     */
    @GetMapping("/property/{id}/rooms-area")
    public ResponseEntity<?> calculateRoomsArea(@PathVariable Integer id) {
        PropertyEntity propertyEntity = propertyService.calculateRoomsArea(id);
        PropertyRoomsResponseDTO response = modelMapper.map(propertyEntity, PropertyRoomsResponseDTO.class);
        return ResponseEntity.ok(response);
    }

}
