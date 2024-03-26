package com.demo.sekolahauth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginRequest {
    @NotEmpty(message = "Username cannot empty")
    private String username;
    @NotEmpty(message = "Password cannot empty")
    private String password;
}
