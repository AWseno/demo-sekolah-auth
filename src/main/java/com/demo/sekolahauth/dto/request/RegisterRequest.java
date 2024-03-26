package com.demo.sekolahauth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RegisterRequest {
    @NotEmpty(message = "Username cannot empty")
    private String username;
    @NotEmpty(message = "Password cannot empty")
    private String password;
    @NotEmpty(message = "Role cannot empty")
    private String role;
    @NotEmpty(message = "Email cannot empty")
    private String email;
    @NotEmpty(message = "Phone cannot empty")
    private String phone;
}
