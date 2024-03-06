package com.example.mybatis.domain;

import com.example.mybatis.web.request.JoinDto;
import com.example.mybatis.web.request.LoginDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersDao {

    public Users login(LoginDto loginDto);
    public void insert(JoinDto joinDto); // DTO 생각해보기
    public Users findById(Integer id);
    public List<Users> findAll();
    public void update(Users users); // DTO 생각해보기
    public void delete(Integer id);
}