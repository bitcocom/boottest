package com.example.backend.controller;

import com.example.backend.entity.Product;
import com.example.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @CrossOrigin
    @PostMapping("/product")
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<Product>(productService.productSave(product), HttpStatus.CREATED);
    }
   @CrossOrigin
    @GetMapping("/product")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(productService.productGetAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/product/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(productService.productGetOne(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/product/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody  Product product){
        return new ResponseEntity<>(productService.productUpdate(id, product), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return new ResponseEntity<>(productService.productDelete(id), HttpStatus.OK);
    }
}
