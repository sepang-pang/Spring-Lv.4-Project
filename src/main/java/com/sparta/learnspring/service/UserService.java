package com.sparta.learnspring.service;


import com.sparta.learnspring.dto.SignupRequestDto;
import com.sparta.learnspring.entity.User;
import com.sparta.learnspring.entity.UserRoleEnum;
import com.sparta.learnspring.exception.RestApiException;
import com.sparta.learnspring.exception.custom.DuplicateException;
import com.sparta.learnspring.exception.custom.InvalidPasswordException;
import com.sparta.learnspring.jwt.JwtUtil;
import com.sparta.learnspring.repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j(topic = "UserService 로그")
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public RestApiException signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new DuplicateException(
                    messageSource.getMessage(
                            "duplicate.user",
                            null,
                            Locale.getDefault()
                    )
            );
        }


        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new DuplicateException(
                    messageSource.getMessage(
                            "duplicate.email",
                            null,
                            Locale.getDefault()
                    )
            );
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new InvalidPasswordException(
                        messageSource.getMessage(
                                "invalid.password",
                                null,
                                Locale.getDefault()
                        )
                );
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
        log.info("사용자 등록 확인");
        log.info("회원가입 성공");
        return new RestApiException("회원가입 성공", HttpStatus.OK.value());
    }
}