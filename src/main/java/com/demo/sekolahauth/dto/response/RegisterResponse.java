package com.demo.sekolahauth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RegisterResponse {
    private Long userId;
    private String username;
    private String phone;
    private String email;
    private String role;
}
