package com.rehneo.bookcreaturebackend.data.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoordinatesCreateDto {

    private Float x;
    @NotNull(message = "the y coordinate cannot be null")
    private Long y;
}
