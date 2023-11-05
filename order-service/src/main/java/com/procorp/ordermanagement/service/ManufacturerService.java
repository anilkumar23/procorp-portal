package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.ManufacturerDto;
import com.procorp.ordermanagement.entities.Manufacturer;

import java.util.Optional;

public interface ManufacturerService {

    Manufacturer create(ManufacturerDto manufacturerDto);

    Optional<Manufacturer> update(Long id,ManufacturerDto manufacturerDto);

    Optional<Manufacturer> getManufacturerById(Long id);

    Iterable<Manufacturer> getAllManufacturerDetails();

    void deleteManufacturerById(Long id);

}
