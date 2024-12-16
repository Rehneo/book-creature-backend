package com.rehneo.bookcreaturebackend.fileimport;

import com.rehneo.bookcreaturebackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileImportMapper {

    private final UserMapper userMapper;

    public FileImportDto map(FileImport fileImport) {
        return FileImportDto.builder()
                .id(fileImport.getId())
                .user(userMapper.map(fileImport.getUser()))
                .status(fileImport.getStatus())
                .addedCount(fileImport.getAddedCount())
                .createdAt(fileImport.getCreatedAt())
                .build();
    }
}
