package com.rehneo.bookcreaturebackend.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserReadDto map(User user){
        return UserReadDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
