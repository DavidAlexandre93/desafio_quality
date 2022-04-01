package br.com.meli.desafio_quality.controller;

import br.com.meli.desafio_quality.dto.PropertyDTO;
import br.com.meli.desafio_quality.dto.PropertyRoomsResponseDTO;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.unit.service.PropertyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class Controller {


    private final PropertyService propertyService;
    private final ModelMapper modelMapper;

    /**
     * @Author Bruno
     * @Description Criar uma nova propriedade no backend da aplicação
     * @param input
     * @return A propriedade criada ou bad Request
     */
    @PostMapping("/new")
    public ResponseEntity<PropertyEntity> createProperty(@Valid @RequestBody PropertyDTO input) {
        PropertyEntity property = propertyService.addProperty(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(property);
    }

    /**
     * @Author Bruno e Pedro
     * @Description Calcular a area total de uma propriedade já registrada
     * @param id de uma propriedade
     * @return o valor da area total se a propriedade estiver cadstrada, ou bad request se não tiver a propriedade com o id cadastrado
     */
    @GetMapping(path = "/totalArea/{id}")
    public ResponseEntity<Double> totalPropertyArea(@PathVariable Integer id) {
        return ResponseEntity.ok(propertyService.totalPropertyArea(id));
    }


    /**
     * @Description Retornar o valor total da propriedade
     * @param id
     * @return
     */
    @GetMapping("/totalpropriedade/{id}")
    public ResponseEntity<BigDecimal> precototalPropriedade(@PathVariable Integer id) {
        return ResponseEntity.ok(propertyService.calculatePrecoAreaTotal(id));
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/biggestRoom/{id}")
    public RoomEntity getBiggestRoom(@PathVariable Integer id) {
        RoomEntity room = propertyService.biggestRoom(id);
        return room;
    }

    /**
     * @Author Maik
     * @Description Busca um imóvel a partir do id fornecido e caso exista retorna a lista de seus cômodos com as respectivas áreas
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
