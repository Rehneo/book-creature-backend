package com.rehneo.bookcreaturebackend.data.dto.create;

import com.rehneo.bookcreaturebackend.data.entity.BookCreatureType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class MagicCityCreateDto {

    @NotNull(message = "the name of the magic city cannot be null")
    @NotBlank(message = "the name of the magic city cannot be blank")
    private String name;

    @NotNull(message = "the area of the magic city cannot be null")
    @Positive(message = "the area of the magic city must be positive")
    private Long area;

    @NotNull(message = "the population of the magic city cannot be null")
    @Positive(message = "the population of the magic city must be positive")
    private Long population;

    private ZonedDateTime establishmentDate;

    @NotNull(message = "the governor of the magic city cannot be null")
    private BookCreatureType governor;

    @NotNull(message = "the capital of the magic city cannot be null")
    private Boolean capital;

    @Positive(message = "the population density of the magic city must be positive")
    private Integer populationDensity;

    @NotNull
    private Boolean editable;
}
