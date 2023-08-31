package com.quangtoi.good_news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.dto.JwtResponse;
import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.request.LoginRequest;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.service.AuthService;
import com.quangtoi.good_news.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        try {
            authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Username or password is invalid!");
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        JwtResponse jwtResponse = authService.login(userDetails);
        return ResponseEntity.ok().body(jwtResponse);
    }

    private void authenticate(String username, String password) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> userRegister(@RequestParam("registerRequest") String registerRequest, @RequestPart("avatar") MultipartFile avatar) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RegisterRequest req = objectMapper.readValue(registerRequest, RegisterRequest.class);
            UserResponse userResponse = authService.userRegister(req, avatar);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
