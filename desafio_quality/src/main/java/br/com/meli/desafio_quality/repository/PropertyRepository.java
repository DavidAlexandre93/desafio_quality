package br.com.meli.desafio_quality.repository;


import br.com.meli.desafio_quality.entity.PropertyEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyRepository {
    private List<PropertyEntity> propertyEntities = new ArrayList<>();

    public PropertyEntity create(PropertyEntity input) {
        propertyEntities.add(input);
        return input;
    }

    public PropertyEntity findById(Integer id) {
        return propertyEntities.stream()
                .filter(propertyEntity -> propertyEntity.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));
    }

}
