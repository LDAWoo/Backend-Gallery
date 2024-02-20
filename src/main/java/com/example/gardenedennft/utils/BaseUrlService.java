package com.example.gardenedennft.utils;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseUrlService {

    private final HttpServletRequest request;
    public String getBaseUrl(){
        return request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getRequestURI()));
    }

}
