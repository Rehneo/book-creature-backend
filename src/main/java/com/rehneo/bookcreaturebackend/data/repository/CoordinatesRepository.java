package com.rehneo.bookcreaturebackend.data.repository;

import com.rehneo.bookcreaturebackend.data.entity.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}
