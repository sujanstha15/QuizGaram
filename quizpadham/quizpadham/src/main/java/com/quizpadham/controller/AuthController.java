package com.quizpadham.controller;

import com.quizpadham.dto.JwtResponse;
import com.quizpadham.dto.LoginRequestDto;
import com.quizpadham.dto.RegisterRequestDto;
import com.quizpadham.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    //api to signup
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(
            @RequestBody RegisterRequestDto registerRequestDto //convert json into regular input
    ){

        String response = authService.registerUser(registerRequestDto);
        if (response.startsWith("Error")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    //login api
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginRequestDto loginRequestDto
            ){
        JwtResponse jwtResponse = authService.loginUser(loginRequestDto);

        if(jwtResponse==null){
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        return ResponseEntity.ok(jwtResponse);

    }
}
