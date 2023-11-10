package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.dto.ProductDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.CategoryRepository;
import com.procorp.ordermanagement.repositories.ProductRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Product> findAllTheProductDetailsBySearchKey(String searchKey) {
        return productRepository.findByNameContaining(searchKey);
    }

    public List<Product> findProductsByCategoryID(Long categoryId) {
        return productRepository.findProductsByCategoryID(categoryId);
    }

    @Override
    public Map<String, List<Product>> getAllProductsGroupByCategory() {
        Map<String, List<Product>> groupByCategory=new HashMap<>();
      Iterable<Product> productIterable= productRepository.findAll();
      if(productIterable!=null){
          groupByCategory= Streamable.of(productRepository.findAll()).toList().stream()
                  .collect(Collectors.groupingBy(p->p.getCategory().getCategoryType()));
      }
     return groupByCategory;
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
         product.setStatus(dto.getStatus());
         product.setDiscount(dto.getDiscount());
         product.setLowStockAlert(dto.getLowStockAlert());
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
            product.setStatus("OutOfStock");
            return productRepository.save(product);
        }else {
            new ResourceNotFoundException("Product not found");
        }
        return null;
    }


}
