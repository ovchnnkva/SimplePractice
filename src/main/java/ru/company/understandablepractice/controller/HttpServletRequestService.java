package ru.company.understandablepractice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.security.services.JwtService;

@Service
@RequiredArgsConstructor
public class HttpServletRequestService {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";
    private final HttpServletRequest request;
    private final JwtService jwtService;

    public Long getIdFromRequestToken(){
        var token = request.getHeader(AUTH_HEADER).substring(BEARER_PREFIX.length());

        return jwtService.extractUserId(token, JwtType.ACCESS);
    }
}
