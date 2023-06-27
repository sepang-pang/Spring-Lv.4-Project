package com.sparta.learnspring.controller;

import com.sparta.learnspring.dto.MsgDto;
import com.sparta.learnspring.dto.SignupRequestDto;
import com.sparta.learnspring.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j(topic = "UserController 로그")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/user/login-page")
//    public String loginPage() {
//        log.info("로그인 페이지입니다.");
//        return "login";
//    }
//
//    @GetMapping("/user/login-page/error")
//    public String error() {
//        log.info("로그인이 실패하였습니다.");
//        return "로그인이 실패하였습니다";
//    }
//
//
//    @GetMapping("/user/signup")
//    public String signupPage() {
//        log.info("회원가입 페이지입니다.");
//        return "signup";
//    }

    @PostMapping("/user/signup")
    public MsgDto signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return new MsgDto("회원가입 실패", HttpStatus.BAD_REQUEST.value());
        }
        return userService.signup(requestDto);
    }

    @GetMapping("/user/forbidden")
    public MsgDto forbidden() {
        log.info("접근 불가입니다.");
        return new MsgDto("권한 없음", HttpStatus.FORBIDDEN.value());
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