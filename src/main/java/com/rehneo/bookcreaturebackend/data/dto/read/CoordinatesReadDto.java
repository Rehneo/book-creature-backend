package com.rehneo.bookcreaturebackend.data.dto.read;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesReadDto {
    private Integer id;
    private Float x;
    private Long y;
}
