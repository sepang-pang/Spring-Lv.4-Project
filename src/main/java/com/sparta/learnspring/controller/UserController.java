package com.sparta.learnspring.controller;

import com.sparta.learnspring.dto.SignupRequestDto;
import com.sparta.learnspring.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@Slf4j(topic = "UserController 로그")
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
    public String signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/user/signup";
        }

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