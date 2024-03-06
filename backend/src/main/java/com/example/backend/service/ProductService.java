package com.example.backend.service;

import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 기능정의, 트랜잭션 관리
@Service
@RequiredArgsConstructor // DI
public class ProductService {
     private final ProductRepository productRepository;

     @Transactional
     public Product productSave(Product product){
          return productRepository.save(product);
     }

     @Transactional(readOnly = true)
     public Product productGetOne(Long id){
          return productRepository.findById(id)
                 .orElseThrow(()->new IllegalArgumentException("id를 확인해 주세요."));
     }
     @Transactional(readOnly = true)
     public List<Product> productGetAll(){
          return productRepository.findAll();
     }
     @Transactional
     public Product productUpdate(Long id, Product product){
          Product productEntiry=productRepository.findById(id)
                  .orElseThrow(()->new IllegalArgumentException("id를 확인해 주세요."));
          productEntiry.setProductName(product.getProductName());
          productEntiry.setProductCompany(product.getProductCompany());
          return productEntiry;
     }
     @Transactional
     public String productDelete(Long id){
          productRepository.deleteById(id);
          return "ok";
     }
}
