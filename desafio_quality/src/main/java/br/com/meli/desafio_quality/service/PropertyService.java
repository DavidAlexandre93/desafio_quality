package br.com.meli.desafio_quality.service;

import br.com.meli.desafio_quality.dto.PropertyDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PropertyService {






    public BigDecimal calculatePropertyValue(PropertyDTO property) {
        return property.getDistrict().getValueDistrictM2().multiply(BigDecimal.valueOf(10));
    }

}
