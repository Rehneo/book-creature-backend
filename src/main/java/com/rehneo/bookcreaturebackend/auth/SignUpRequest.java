package com.rehneo.bookcreaturebackend.auth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Size(min = 4, max = 128, message = "The username must contain from 4 to 128 characters")
    @NotBlank(message = "The username cannot be blank")
    private String username;

    @Size(min = 6, max = 128, message = "The password must contain from 6 to 128 characters")
    @NotBlank(message = "The password cannot be blank")
    private String password;
}
