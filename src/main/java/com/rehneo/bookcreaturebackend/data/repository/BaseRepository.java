package com.rehneo.bookcreaturebackend.data.repository;

import com.rehneo.bookcreaturebackend.data.entity.AuditableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends AuditableEntity<K>,
        K extends Serializable> extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {

    Page<T> findAllByOwnerId(Pageable pageable, Integer ownerId);
}
