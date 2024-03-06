package com.example.backend.controller;

import com.example.backend.entity.Product;
import com.example.backend.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 단위 테스트 (Controller, Filter, ControllerAdvice... 관련 로직만 띄우기)
@WebMvcTest
//@ExtendWith(SpringExtension.class)
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc; // Controller안에있는 Service객체생성 불가

    @MockBean // IoC환경에 Bean등록(가짜)
    private ProductService productService;

    // BDDMockito
    @Test
    public void saveTest() throws Exception {
       // given(테스트 준비)
        Product product=new Product(null, "세탁기","삼성전자");
        String content=new ObjectMapper().writeValueAsString(product);
        // 스텁(미리 행동을 지정) when
        when(productService.productSave(product)).thenReturn(new Product(1L,"세탁기","삼성전자"));
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
        productList.add(new Product(1L,"세탁기","삼성전자"));
        productList.add(new Product(2L,"냉장고","LG전자"));
        when(productService.productGetAll()).thenReturn(productList);
      // when
        ResultActions resultAction=mockMvc.perform(get("/product")
                .accept(MediaType.APPLICATION_JSON_UTF8));
        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].productName").value("세"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findByIdTest() throws Exception{
        // given
        Long id=1L;
        when(productService.productGetOne(id))
                .thenReturn(new Product(1L, "빔프로젝트","삼성전자"));
        // when
        ResultActions resultAction= mockMvc.perform(get("/product/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8));
        // then
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("빔프로젝트"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateTest() throws Exception{
        // given
        Long id=1L;
        Product product=new Product(null, "AI세탁기","삼성전자");
        String content=new ObjectMapper().writeValueAsString(product);
        when(productService.productUpdate(id, product)).thenReturn(new Product(1L,"AI세탁기","삼성전자"));

        // when
        ResultActions resultAction= mockMvc.perform(put("/product/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("AI세탁기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteByIdTest() throws Exception{
        // given
        Long id=1L;
        when(productService.productDelete(id)).thenReturn("ok");
        // when
        ResultActions resultAction= mockMvc.perform(delete("/product/{id}", id)
                .accept(MediaType.TEXT_PLAIN));

        // then
        resultAction.andExpect(status().isOk())
                  .andDo(MockMvcResultHandlers.print());

        MvcResult requestResult=resultAction.andReturn();
        String result=requestResult.getResponse().getContentAsString();

        assertEquals("ok", result);
    }
}
