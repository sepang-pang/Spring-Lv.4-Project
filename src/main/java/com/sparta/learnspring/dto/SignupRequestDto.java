package com.sparta.learnspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 작성해주십시오.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\\\"\\\\\\\\|,.<>\\\\/?]).{8,15}$", message = "최소 8자 이상, 특수문자 포함 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 작성해주십시오")
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\w+@\\w+\\.\\w+$", message = "올바른 형식이 아닙니다.")
    private String email;

    private boolean admin = false;

    private String adminToken = "";
}