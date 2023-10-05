package com.quangtoi.good_news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.dto.JwtResponse;
import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.LoginRequest;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.request.ThirdPartyRequest;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid final LoginRequest loginRequest){
        try {
            authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Username or password is invalid!");
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        JwtResponse jwtResponse = authService.login(userDetails);
        return ResponseEntity.ok().body(jwtResponse);
    }

    private void authenticate(final String username, final String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PostMapping(value = "/api/v1/auth/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> userRegister(
            @RequestParam("registerRequest") final String registerRequest,
            @RequestPart("avatar") final MultipartFile avatar) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RegisterRequest req = objectMapper.readValue(registerRequest, RegisterRequest.class);
            UserResponse userResponse = authService.userRegister(req, avatar);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/api/v1/auth/google-login")
    public ResponseEntity<?> addUserInfoGoogleLogin(@RequestBody ThirdPartyRequest thirdPartyPayload) {

        JwtResponse jwtResponse = authService.thirdPartyService(thirdPartyPayload);
        return ResponseEntity.ok(jwtResponse);
    }
}
