package com.rehneo.bookcreaturebackend.data.dto.create;
import com.rehneo.bookcreaturebackend.data.entity.BookCreatureType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookCreatureCreateDto {

    @NotNull(message = "the name of the book creature cannot be null")
    @NotBlank(message = "the name of the book creature cannot be blank")
    private String name;

    @NotNull(message = "the coordinates of the book creature cannot be null")
    private CoordinatesCreateDto coordinates;

    @NotNull(message = "the age of the book creature cannot be null")
    @Positive(message = "the age of the book creature must be positive")
    private Long age;

    private BookCreatureType type;

    @NotNull(message = "the location of the book creature cannot be null")
    private Integer locationId;

    @Positive(message = "the attack level of the book creature must be positive")
    private Integer attackLevel;

    @Positive(message = "the defense level of the book creature must be positive")
    private Long defenseLevel;

    @NotNull(message = "the ring of the book creature cannot be null")
    private Long ringId;

    @NotNull
    private Boolean editable;

}
