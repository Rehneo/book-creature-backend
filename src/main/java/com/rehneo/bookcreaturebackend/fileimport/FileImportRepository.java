package com.rehneo.bookcreaturebackend.fileimport;

import com.rehneo.bookcreaturebackend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileImportRepository extends JpaRepository<FileImport, Integer> {

    Page<FileImport> findAllByUser(User user, Pageable pageable);

}
