package com.rehneo.bookcreaturebackend.data.dto.read;
import com.rehneo.bookcreaturebackend.user.UserReadDto;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;


@Getter
@Setter
public abstract class AuditableDto {
    private Boolean editable;
    private UserReadDto owner;
    private ZonedDateTime createdAt;
    private UserReadDto updatedBy;
    private ZonedDateTime updatedAt;
}
