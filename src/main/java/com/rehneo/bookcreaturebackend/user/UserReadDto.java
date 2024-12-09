package com.rehneo.bookcreaturebackend.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDto {
    private int id;
    private String username;
    private Role role;
    private ZonedDateTime createdAt;
}
