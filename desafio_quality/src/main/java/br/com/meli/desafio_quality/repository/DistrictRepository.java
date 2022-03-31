package br.com.meli.desafio_quality.repository;


import br.com.meli.desafio_quality.dto.DistrictDTO;
import br.com.meli.desafio_quality.entity.DistrictEntity;
import br.com.meli.desafio_quality.entity.PropertyEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DistrictRepository {


    private List<DistrictEntity> districts = new ArrayList() {
        {
        add(new DistrictEntity("Alto Branco", BigDecimal.valueOf(10)));
        add(new DistrictEntity("Centro", BigDecimal.valueOf(5)));
        add(new DistrictEntity("Conceição", BigDecimal.valueOf(15)));
        add(new DistrictEntity("Dinamerica", BigDecimal.valueOf(5)));
        add(new DistrictEntity("Liberdade", BigDecimal.valueOf(10)));
        }
    };


    /**
     *
     * @param district
     */
    public void add(DistrictEntity district){
        districts.add(district);

    }

    /**
     *
     * @return
     */
    public List<DistrictEntity> findAll(){
        return districts;
    }



}
