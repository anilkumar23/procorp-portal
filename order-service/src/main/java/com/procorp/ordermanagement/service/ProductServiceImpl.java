package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.dto.ProductDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.CategoryRepository;
import com.procorp.ordermanagement.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
         product.setPrice(dto.getPrice());
         product.setPictureUrl(dto.getPictureUrl());
         product.setUom(dto.getUom());
         Optional<Category> category= this.categoryRepository.findById(dto.getCategoryTypeId());
         if(category.isPresent()){
             product.setCategory(category.get());
         }else{
             new ResourceNotFoundException("Category not found");
         }
          return productRepository.save(product);
     }else {
         new ResourceNotFoundException("Product not found");
     }
        return null;
    }

    @Override
    public Product updateProductStatus(Long id){

        Optional<Product> existingProduct= productRepository.findById(id);
        if(existingProduct.isPresent()){
            Product product=existingProduct.get();
           // product.setStatus("OutOfStock");
            return productRepository.save(product);
        }else {
            new ResourceNotFoundException("Product not found");
        }
        return null;
    }


}
