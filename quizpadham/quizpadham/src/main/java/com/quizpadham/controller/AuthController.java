package com.quizpadham.controller;

import com.quizpadham.dto.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //api to signup
    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(
            @RequestBody RegisterRequestDto registerRequestDto
    ){

    return null;
    }
}
