package com.quizpadham.service;

import com.quizpadham.config.JwtUtils;
import com.quizpadham.dto.JwtResponse;
import com.quizpadham.dto.LoginRequestDto;
import com.quizpadham.dto.RegisterRequestDto;
import com.quizpadham.entity.User;
import com.quizpadham.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    //Register a new user
    public String registerUser(RegisterRequestDto registerRequestDto){
        if(userRepository.existsByEmail(registerRequestDto.getEmail())){
            return "Error: Email is already in use!";
        }

        if(userRepository.existsByUsername(registerRequestDto.getUsername())){
            return "Error: Username is already taken!";
        }

        //if the email and username does not exist already, we will create a new user
        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));//hashing the password
        user.setRole("USER");

        userRepository.save(user);

        return "User registered successfully";

    }

    //Login user and generate JWT
    public JwtResponse loginUser(LoginRequestDto loginRequestDto){
        Optional<User> userOpt = userRepository.findByEmail(loginRequestDto.getEmail());
        if(userOpt.isEmpty()){
            return null;
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return null; // Password mismatch
        }

        String token = jwtUtils.generateToken(user.getUsername(), user.getRole());

        return new JwtResponse(token, user.getUsername(), user.getRole());
    }


}
