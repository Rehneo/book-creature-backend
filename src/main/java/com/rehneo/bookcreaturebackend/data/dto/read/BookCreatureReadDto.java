package com.rehneo.bookcreaturebackend.data.dto.read;

import com.rehneo.bookcreaturebackend.data.entity.BookCreatureType;
import lombok.*;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCreatureReadDto extends AuditableDto{
    private Long id;
    private String name;
    private CoordinatesReadDto coordinates;
    private Long age;
    private BookCreatureType type;
    private MagicCityReadDto location;
    private Integer attackLevel;
    private Long defenseLevel;
    private RingReadDto ring;

}
