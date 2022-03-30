package br.com.meli.desafio_quality.repository;


import br.com.meli.desafio_quality.entity.PropertyEntity;
<<<<<<< HEAD
=======
import org.springframework.http.HttpStatus;
>>>>>>> 6c41c021c460348bff553359313f481be3c4e025
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyRepository {
<<<<<<< HEAD

    private PropertyEntity propertyEntity;

    public PropertyEntity getProperty(){
        return propertyEntity;

    }

=======
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
>>>>>>> 6c41c021c460348bff553359313f481be3c4e025

}
