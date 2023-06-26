package com.sparta.learnspring.controller;

import com.sparta.learnspring.dto.SignupRequestDto;
import com.sparta.learnspring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login-page")
    @ResponseBody
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/login-page/error")
    @ResponseBody
    public String error() {
        return "로그인이 실패하였습니다";
    }


    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("user/signup")
    public String signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return "redirect:/api/user/login-page";
    }

    @GetMapping("/user/forbidden")
    @ResponseBody
    public String forbidden() {
        return "접근 불가입니다.";
    }

//    @PostMapping("/user/login")
//    public String login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
//        try {
//            userService.login(requestDto, res);
//        } catch (Exception e) {
//            return "redirect:/api/user/login-page/error";
//        }
//        return "redirect:/api/user/login-page";
//    }
}