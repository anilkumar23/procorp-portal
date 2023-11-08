package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.Buyer_AddressDto;
import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.entities.Buyer_Address;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.BuyerAddressRepository;
import com.procorp.ordermanagement.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BuyerAddressServiceImpl implements BuyerAddressService {

    private BuyerAddressRepository buyerAddressRepository;

    public BuyerAddressServiceImpl(BuyerAddressRepository buyerAddressRepository){
        this.buyerAddressRepository=buyerAddressRepository;
    }

    @Override
    public Buyer_Address create(Buyer_AddressDto dto) {
        Buyer_Address buyer_Address=new Buyer_Address();
        buyer_Address.setAddress(dto.getAddress());
        buyer_Address.setPincode(dto.getPincode());
        buyer_Address.setUser_id(dto.getUser_id());
        return this.buyerAddressRepository.save(buyer_Address);
    }

    @Override
    public Optional<Buyer_Address> update(Long id, Buyer_AddressDto dto) {
        Optional<Buyer_Address> existingBuyerAddress = this.buyerAddressRepository.findById(id);
        if(existingBuyerAddress.isPresent()){
            Buyer_Address exisitng = existingBuyerAddress.get();
            exisitng.setAddress(dto.getAddress());
            exisitng.setUser_id(dto.getUser_id());
            exisitng.setPincode(dto.getPincode());
            return Optional.of(this.buyerAddressRepository.save(exisitng));
        }else{
            throw new ResourceNotFoundException("Buyer_Address was not found with given ID: "+id);
        }
    }

    @Override
    public Optional<Buyer_Address> getBuyerAddressById(Long id){
        Optional<Buyer_Address> buyer_Address = this.buyerAddressRepository.findById(id);
        if(buyer_Address.isPresent()){
            return buyer_Address;
        }else{
            throw new ResourceNotFoundException("buyer_Address was not found with given ID: "+id);
        }
    }

    @Override
    public Iterable<Buyer_Address> getAllBuyerAddressDetails(){
        return  this.buyerAddressRepository.findAll();
    }

    @Override
    public void deleteBuyerAddressById(Long id){
        this.buyerAddressRepository.deleteById(id);
    }

}
