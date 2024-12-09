package com.rehneo.bookcreaturebackend.data.dto.read;


import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RingReadDto extends AuditableDto{
    private Long id;
    private String name;
    private Long power;
}
