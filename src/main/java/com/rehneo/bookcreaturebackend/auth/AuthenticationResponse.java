package com.rehneo.bookcreaturebackend.auth;
import com.rehneo.bookcreaturebackend.user.UserReadDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UserReadDto user;
}
