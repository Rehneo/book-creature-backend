package com.rehneo.bookcreaturebackend.data.dto.read;

import com.rehneo.bookcreaturebackend.data.entity.BookCreatureType;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MagicCityReadDto extends AuditableDto {
    private Integer id;
    private String name;
    private Long area;
    private Long population;
    private ZonedDateTime establishmentDate;
    private BookCreatureType governor;
    private Boolean capital;
    private Integer populationDensity;
}
