package com.example.backend.controller;

import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 통합 테스트(모든 Bean들을 똑같이 IoC에 올리고 테스트 한는 것)
// WebEnvironment.MOCK : 실제 톰켓을 올리는게 아니라, 다른 톰켓으로 테스트
@Transactional // 각각의 테스트 함수가 종료될 때마다 트랜젝션을 rollback해주는 어노테이션
@AutoConfigureMockMvc // MockMvc를 IoC에 등록해 줌
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductControllerIntegreTest {
    // MVC 테스트(Mokito)
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init(){
        entityManager.createNativeQuery("ALTER TABLE product AUTO_INCREMENT = 1").executeUpdate();
    }
    // BDDMockito
    @Test
    public void saveTest() throws Exception {
        // given(테스트 준비)
        Product product=new Product(null, "세탁기","삼성전자");
        String content=new ObjectMapper().writeValueAsString(product);
       // 테스트실행
        ResultActions resultAction=mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));
        // then(검증)
        resultAction
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("세탁기" ))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findAllTest() throws Exception{
        // given
        List<Product> productList=new ArrayList<>();
        productList.add(new Product(null,"세탁기","삼성전자"));
        productList.add(new Product(null,"냉장고","LG전자"));
        productList.add(new Product(null,"에어컨","삼성전자"));

        productRepository.saveAll(productList);
        // when
        ResultActions resultAction=mockMvc.perform(get("/product")
                .accept(MediaType.APPLICATION_JSON_UTF8));
        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[2].productName").value("에어컨"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findByIdTest() throws Exception{
        // given
        Long id=2L;

        List<Product> productList=new ArrayList<>();
        productList.add(new Product(null,"세탁기","삼성전자"));
        productList.add(new Product(null,"냉장고","LG전자"));
        productList.add(new Product(null,"에어컨","삼성전자"));

        productRepository.saveAll(productList);

        // when
        ResultActions resultAction= mockMvc.perform(get("/product/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8));
        // then
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("냉장고"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateTest() throws Exception{
        // given
        Long id=3L;
        List<Product> productList=new ArrayList<>();
        productList.add(new Product(null,"세탁기","삼성전자"));
        productList.add(new Product(null,"냉장고","LG전자"));
        productList.add(new Product(null,"에어컨","삼성전자"));

        productRepository.saveAll(productList);

        Product product=new Product(null, "컴퓨터","삼성전자");
        String content=new ObjectMapper().writeValueAsString(product);
        // when
        ResultActions resultAction= mockMvc.perform(put("/product/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.productName").value("컴퓨터"))
                .andDo(MockMvcResultHandlers.print());
    }
}
