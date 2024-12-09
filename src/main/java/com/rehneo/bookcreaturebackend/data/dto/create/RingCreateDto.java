package com.rehneo.bookcreaturebackend.data.dto.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RingCreateDto {
    @NotNull(message = "the name of the ring cannot be null")
    @NotBlank(message = "the name of the ring cannot be blank")
    private String name;

    @NotNull(message = "power cannot be null")
    @Positive(message = "power must be positive")
    private Long power;

    @NotNull
    private Boolean editable;
}
