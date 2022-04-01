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
    private static Integer idAdd = 0;

    /**
     * @Author Bruno
     * @Description Criar uma nova propriedade na lista
     * @param input
     * @return a propriedade cadstrada
     */
    public PropertyEntity create(PropertyEntity input) {
        propertyEntities.add(input);
        input.setId(idAdd);
        idAdd ++;
        return input;
    }

    /**
     * @Author Bruno e Pedro
     * @Description retorna uma propriedade da lista passando o id
     * @param id
     * @return a propriedade buscada pelo id ou lança um erro de propriedade não encontrada
     */
    public PropertyEntity findById(Integer id) {
        return propertyEntities.stream()
                .filter(propertyEntity -> propertyEntity.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Propriedade não encontrada"));
    }

    /**
     * @Author Bruno
     * @Description Retorna todas as propriedades cadastradas
     * @return lista de propriedades cadastradas
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
        idAdd = 0;
    }
}
