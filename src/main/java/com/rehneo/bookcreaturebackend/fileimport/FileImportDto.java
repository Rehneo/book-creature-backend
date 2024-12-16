package com.rehneo.bookcreaturebackend.fileimport;

import com.rehneo.bookcreaturebackend.user.UserReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileImportDto {
    private int id;
    private UserReadDto user;
    private Status status;
    private Integer addedCount;
    private ZonedDateTime createdAt;
}
