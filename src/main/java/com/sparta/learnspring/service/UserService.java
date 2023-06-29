package com.sparta.learnspring.service;


import com.sparta.learnspring.dto.MsgDto;
import com.sparta.learnspring.dto.SignupRequestDto;
import com.sparta.learnspring.entity.User;
import com.sparta.learnspring.entity.UserRoleEnum;
import com.sparta.learnspring.jwt.JwtUtil;
import com.sparta.learnspring.repoistory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j(topic = "UserService 로그")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public MsgDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        try {
            Optional<User> checkUsername = userRepository.findByUsername(username);
            if (checkUsername.isPresent()) {
                throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            }
        } catch (IllegalArgumentException e) {
            return new MsgDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }


        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
        log.info("사용자 등록 확인");
        log.info("회원가입 성공");
        return new MsgDto("회원가입 성공", HttpStatus.OK.value());
    }

//    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
//        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
//
//        // 사용자 확인
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
//        );
//
//        // 비밀번호 확인
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
//        }
//
//        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
//        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
//        jwtUtil.addJwtToCookie(token, res);
//    }
}