package com.example.backend.service;

import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// 단위 테스트 (Service와 관련된 것만 메모리에 띄우면 됨)
@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {
    // ProductService객체가 만들어질때
    // ProductServiceUnitTest파일에 @Mock로 등록된 모든 애들을 주입받는다.
    @InjectMocks
    private ProductService productService; // 메모리에 올리기
    @Mock
    private ProductRepository productRepository; // 가짜 객체로 만들 수 있음

    @Test
    public void saveTest(){
        Product product=new Product();
        product.setProductName("김치냉장고");
        product.setProductCompany("LG전자");
        when(productRepository.save(product)).thenReturn(product);

        Product productEntity=productService.productSave(product);

        assertEquals(productEntity, product);
    }
}
