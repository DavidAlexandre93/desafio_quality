package br.com.meli.desafio_quality.controller;

import br.com.meli.desafio_quality.entity.RoomEntity;
import br.com.meli.desafio_quality.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/seuImovel")
@RestController
public class Controller {
    @Autowired
    private PropertyService propertyService;

    @GetMapping("/biggestRoom")
    public RoomEntity getBiggestRoom() {
        RoomEntity rooms = propertyService.biggestRoom();
        return rooms;

    }

}
