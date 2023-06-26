package com.sparta.learnspring.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.io.IOException;

@Slf4j(topic = "LoggingFilter")
/*@Component*/
@Order(1)
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 전처리
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        log.info(url);
        // ServletRequest 의 역할과 이를 HttpServletRequest로 변환시키는 이유
        // url 로그를 찍는 이유
        chain.doFilter(request, response); // 다음 Filter 로 이동

        // 후처리
        log.info("비즈니스 로직 완료");
    }
}