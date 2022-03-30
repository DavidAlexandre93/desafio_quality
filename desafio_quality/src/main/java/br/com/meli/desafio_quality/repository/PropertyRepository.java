package br.com.meli.desafio_quality.repository;


import br.com.meli.desafio_quality.entity.PropertyEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyRepository {

    private PropertyEntity propertyEntity;

    public PropertyEntity getProperty(){
        return propertyEntity;

    }


}
