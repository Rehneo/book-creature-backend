package com.rehneo.bookcreaturebackend.data.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "magic_cities")
public class MagicCity extends AuditableEntity<Integer> {

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Positive
    @Column(name = "area", nullable = false)
    private Long area;

    @NotNull
    @Positive
    @Column(name = "population", nullable = false)
    private Long population;

    @Column(name = "establishment_date")
    private ZonedDateTime establishmentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @ColumnTransformer(write="?::book_creature_type")
    @Column(name="governor", nullable=false)
    private BookCreatureType governor;

    @NotNull
    @Column(name = "capital", nullable = false)
    private Boolean capital;

    @Positive
    @Column(name = "population_density")
    private Integer populationDensity;

}
