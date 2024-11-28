package ru.company.understandablepractice.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpRequest {
    @Schema(example = "anna")
    private String username;
    @Schema(example = "pass")
    private String password;
}
