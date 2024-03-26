package com.demo.sekolahauth.controller;

import com.demo.sekolahauth.dto.request.LoginRequest;
import com.demo.sekolahauth.dto.request.RegisterRequest;
import com.demo.sekolahauth.http.response.MessageResponse;
import com.demo.sekolahauth.repository.UserAuthenticationRepo;
import com.demo.sekolahauth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.OK.value(), "Login success",
                authenticationService.login(loginRequest), null), HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.OK.value(), "Register success",
                authenticationService.register(registerRequest, httpServletRequest.getRemoteAddr()), null), HttpStatus.OK);
    }

    @GetMapping(value = "/redis", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResponse> redis(@RequestHeader("Authorization") String authHeader) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.OK.value(), "get redis success",
                authenticationService.redis(authHeader), null), HttpStatus.OK);
    }
}
