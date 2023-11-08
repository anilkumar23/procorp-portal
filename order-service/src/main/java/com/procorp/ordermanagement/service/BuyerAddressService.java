package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.Buyer_AddressDto;
import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.entities.Buyer_Address;
import com.procorp.ordermanagement.entities.Category;

import java.util.Optional;

public interface BuyerAddressService {

    Buyer_Address create(Buyer_AddressDto dto);

    Optional<Buyer_Address> update(Long id,Buyer_AddressDto dto);

    Optional<Buyer_Address> getBuyerAddressById(Long id);

    Iterable<Buyer_Address> getAllBuyerAddressDetails();

    void deleteBuyerAddressById(Long id);

}
