package com.rehneo.bookcreaturebackend.data.repository;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCreatureRepository extends BaseRepository<BookCreature, Long> {

    Page<BookCreature> findByIdIn(Pageable pageable, Long... ids);

    @Query(value = "select avg_attack_level()", nativeQuery = true)
    double avgAttackLevel();

    @Query(value = "select exchange_rings(:id1, :id2)", nativeQuery = true)
    void exchangeRings(long id1, long id2);


    @Query(value = "select * from book_creatures where id in (select creature_ids_by_name_containing(:name))", nativeQuery = true)
    Page<BookCreature> findByNameContaining(String name, Pageable pageable);

    @Query(value = "select * from book_creatures where id in (select creature_ids_by_name_starting_with(:name))", nativeQuery = true)
    Page<BookCreature> findByNameStartingWith(String name, Pageable pageable);


    @Query(value = "select * from book_creatures where id in (select creature_ids_with_strongest_ring())", nativeQuery = true)
    Page<BookCreature> findByStrongestRing(Pageable pageable);
}
