package com.rehneo.bookcreaturebackend.admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface AdminRequestRepository extends JpaRepository<AdminRequest, Integer> {
    Page<AdminRequest> findAllByOrderByRequestDateDesc(Pageable pageable);

    Page<AdminRequest> findAllByStatusOrderByRequestDateDesc(Status status, Pageable pageable);

    Optional<AdminRequest> findByUserId(int userId);
}
