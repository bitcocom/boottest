package com.example.backend.repository;

import com.example.backend.entity.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// 단위 테스트 (DB관련 Bean이 IoC에 등록이 되면 된다)
// Replace.ANY 가짜 DB로 테스트
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE )
@DataJpaTest // Repository들을 다 IoC에 등록해줌
public class ProductRepositoryUnitTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveTest(){
        Product product=new Product(null,"김치냉장고","LG전자");

        Product productEntity=productRepository.save(product);

        assertEquals("김치냉장고",productEntity.getProductName());
    }

}
