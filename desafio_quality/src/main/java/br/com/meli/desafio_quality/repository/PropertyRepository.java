package br.com.meli.desafio_quality.repository;


import br.com.meli.desafio_quality.entity.PropertyEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyRepository {
    private static final List<PropertyEntity> propertyEntities = new ArrayList<>();

    /**
     *
     * @param input
     * @return
     */
    public PropertyEntity create(PropertyEntity input) {
        propertyEntities.add(input);
        return input;
    }

    /**
     *
     * @param id
     * @return
     */
    public PropertyEntity findById(Integer id) {
        return propertyEntities.stream()
                .filter(propertyEntity -> propertyEntity.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));
    }

    /**
     *
     * @param
     * @return
     */
    public List<PropertyEntity> findAll() {
        if (propertyEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nenhuma propriedade cadastrada");
        } else {
            return propertyEntities;
        }
    }

    /**
     * Método utilizado para remover todos os elementos armazenados no repositório.
     */
    public void clear() {
        propertyEntities.clear();
    }

}
