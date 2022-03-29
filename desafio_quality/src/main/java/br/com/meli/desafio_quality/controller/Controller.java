package br.com.meli.desafio_quality.controller;

import br.com.meli.desafio_quality.dto.PropertyDTO;
import br.com.meli.desafio_quality.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

//@RequestMapping("/seuImovel")
@RestController
public class Controller {


    PropertyService service = new PropertyService();


    @PostMapping("/value")
    public String analisaValorPropriedade(@RequestBody PropertyDTO propertyDTO) {
        return "O preço total da propriedade é igual a " + service.calculatePropertyValue(propertyDTO);
    }

//    @PostMapping("/value")
//    public PropertyDTO analisaValorPropriedade(@RequestBody PropertyDTO propertyDTO) {
//        return propertyDTO;
//    }





}
