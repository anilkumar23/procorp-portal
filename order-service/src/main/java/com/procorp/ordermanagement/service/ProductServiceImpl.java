package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.dto.ProductDto;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Long id, ProductDto dto){

     Optional<Product> existingProduct= productRepository.findById(id);
     if(existingProduct.isPresent()){
         Product product=existingProduct.get();
         product.setName(dto.getName());
         product.setSex(dto.getSex());
         product.setProductType(dto.getProductType());
         product.setPrice(dto.getPrice());
         product.setPictureUrl(dto.getPictureUrl());
          return productRepository.save(product);
     }else {
         new ResourceNotFoundException("Product not found");
     }
        return null;
    }


}
