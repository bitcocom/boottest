package com.example.mybatis.web;


import com.example.mybatis.domain.Users;
import com.example.mybatis.domain.UsersDao;
import com.example.mybatis.web.request.JoinDto;
import com.example.mybatis.web.request.LoginDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class UsersController{

    private final HttpSession session;
    private final UsersDao usersDao;

    @GetMapping( "/")
    public String index(){
        return "main/main";
    }
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login") // 로그인만 예외로 select인데 post로 함.
    public String login(LoginDto loginDto) {
        Users usersPS = usersDao.login(loginDto);

        if(usersPS != null) { // 인증됨.
            session.setAttribute("principal", usersPS);
            return "redirect:/";
        }else { // 인증안됨.
            return "redirect:/loginForm";
        }
    }
    @PostMapping("/join")
    public String join(JoinDto joinDto) {
        System.out.println("/join 호출시 ================");
        System.out.println("username : "+joinDto.getUsername());
        System.out.println("password : "+joinDto.getPassword());
        System.out.println("email : "+joinDto.getEmail());
        System.out.println("================");
        usersDao.insert(joinDto);
        return "redirect:/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "users/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        System.out.println("OK");
        return "users/joinForm";
    }
}