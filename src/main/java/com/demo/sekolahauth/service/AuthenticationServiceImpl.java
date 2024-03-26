package com.demo.sekolahauth.service;

import com.demo.sekolahauth.config.TokenService;
import com.demo.sekolahauth.dto.request.LoginRequest;
import com.demo.sekolahauth.dto.request.RegisterRequest;
import com.demo.sekolahauth.dto.response.LoginResponse;
import com.demo.sekolahauth.dto.response.RegisterResponse;
import com.demo.sekolahauth.entity.User;
import com.demo.sekolahauth.enumeration.Role;
import com.demo.sekolahauth.http.exception.AuthException;
import com.demo.sekolahauth.model.UserAuthentication;
import com.demo.sekolahauth.repository.UserAuthenticationRepo;
import com.demo.sekolahauth.repository.UserRepo;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepo userRepo;
    private final UserAuthenticationRepo userAuthenticationRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if(authentication.isAuthenticated()){
            User user = userRepo.findByUsername(loginRequest.getUsername()).orElseThrow();
            String token = tokenService.generateToken(user);

            UserAuthentication userAuthentication = UserAuthentication.builder()
                    .username(user.getUsername())
                    .userid(user.getId())
                    .token(token)
                    .authorities(user.getAuthorities())
                    .timeToLeave(tokenService.extractExpiration(token).getTime())
                    .build();
            userAuthenticationRepo.save(userAuthentication);

            log.info("log in user " + user.getUsername() + " token " + token);

            return LoginResponse.builder()
                    .token(token)
                    .userId(user.getId())
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid");
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest, String remoteAddr) {
        User savedUser = saveUser(validateRequest(registerRequest), "API", remoteAddr);
        return RegisterResponse.builder()
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .phone(savedUser.getUsername())
                .userId(savedUser.getId())
                .role(savedUser.getRole().name())
                .build();
    }

    private User saveUser(RegisterRequest request, String createdBy, String from) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setUsername(request.getUsername());
        user.setRole(Role.valueOf(request.getRole()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setCreatedDate(new Date());
        user.setCreatedBy(createdBy);
        user.setCreatedFrom(from);

        user.setLastUpdatedDate(new Date());
        user.setLastUpdatedBy(createdBy);
        user.setLastUpdatedFrom(from);

        user.setRecordStatus('A');
        return userRepo.save(user);
    }

    private RegisterRequest validateRequest(RegisterRequest registerRequest) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(registerRequest.getEmail());
        if (!matcher.matches()) {
            log.info("validate request register email invalid");
            throw new AuthException("400", "Invalid email", "Invalid email");
        }

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber;
        try {
            phoneNumber = phoneNumberUtil.parse(registerRequest.getPhone(), "ID");
        } catch (NumberParseException e) {
            log.info("validate request register phone parse failed");
            throw new AuthException("400", "Failed parse phone", "Failed parse phone");
        }
        if (!phoneNumberUtil.isValidNumber(phoneNumber)) {
            log.info("validate request register phone invalid");
            throw new AuthException("400", "Invalid phone", "Invalid phone");
        }
        registerRequest.setPhone(phoneNumber.getCountryCode() + String.valueOf(phoneNumber.getNationalNumber()));

        try {
            Role.valueOf(registerRequest.getRole());
        } catch (IllegalArgumentException e) {
            log.info("validate request register role invalid");
            throw new AuthException("400", "Invalid role", "Invalid role");
        }

        if (userRepo.findByUsername(registerRequest.getUsername()).isPresent()) {
            log.info("validate request register username exist");
            throw new AuthException("400", "Username exist", "Username exist");
        }

        return registerRequest;
    }

    @Override
    public UserAuthentication redis(String authHeader) {
        return userAuthenticationRepo.findById(authHeader.substring(7)).orElse(null);
    }
}
