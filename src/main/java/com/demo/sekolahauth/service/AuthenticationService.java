package com.demo.sekolahauth.service;

import com.demo.sekolahauth.dto.request.LoginRequest;
import com.demo.sekolahauth.dto.request.RegisterRequest;
import com.demo.sekolahauth.dto.response.LoginResponse;
import com.demo.sekolahauth.dto.response.RegisterResponse;
import com.demo.sekolahauth.model.UserAuthentication;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest);

    RegisterResponse register(RegisterRequest registerRequest, String remoteAddr);

    UserAuthentication redis(String authHeader);
}
