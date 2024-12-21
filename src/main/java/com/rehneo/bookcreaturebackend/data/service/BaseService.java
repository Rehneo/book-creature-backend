package com.rehneo.bookcreaturebackend.data.service;

import com.rehneo.bookcreaturebackend.data.dto.read.AuditableDto;
import com.rehneo.bookcreaturebackend.data.entity.AuditableEntity;
import com.rehneo.bookcreaturebackend.data.search.SearchCriteriaDto;
import com.rehneo.bookcreaturebackend.data.search.SearchMapper;
import com.rehneo.bookcreaturebackend.error.AccessDeniedException;
import com.rehneo.bookcreaturebackend.error.ResourceNotFoundException;
import com.rehneo.bookcreaturebackend.data.mapper.BaseMapper;
import com.rehneo.bookcreaturebackend.data.repository.BaseRepository;
import com.rehneo.bookcreaturebackend.user.Role;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.ZonedDateTime;


@AllArgsConstructor
public abstract class BaseService<
        T extends AuditableEntity<K>,
        K extends Serializable,
        TReadDto extends AuditableDto,
        TCreateDto,
        TMapper extends BaseMapper<T, K, TReadDto, TCreateDto>,
        TRepository extends BaseRepository<T, K>> {
    protected final TRepository repository;
    protected final TMapper mapper;
    protected UserService userService;
    private final SearchMapper<T> searchMapper;

    public TReadDto findById(K id) {
        T entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found: " + id)
        );
        return mapper.map(entity);
    }


    @Transactional
    public TReadDto create(TCreateDto createDto) {
        T entity = mapper.map(createDto);
        User user = userService.getCurrentUser();
        entity.setOwner(user);
        entity.setCreatedAt(ZonedDateTime.now());
        repository.save(entity);
        return mapper.map(entity);
    }

    @Transactional
    public void delete(K id) {
        T entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found: " + id)
        );
        User user = userService.getCurrentUser();
        if (
                (user.getRole() == Role.ADMIN && entity.getEditable()) ||
                        user.getId() == entity.getOwner().getId()) {
            repository.delete(entity);
        } else {
            throw new AccessDeniedException("not enough rights to delete this entity");
        }
    }

    @Transactional
    public TReadDto update(K id, TCreateDto createDto) {
        T entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found: " + id)
        );
        User user = userService.getCurrentUser();
        if (
                (user.getRole() == Role.ADMIN && entity.getEditable()) ||
                        user.getId() == entity.getOwner().getId()) {
            mapper.update(entity, createDto);
            entity.setUpdatedBy(userService.getCurrentUser());
            entity.setUpdatedAt(ZonedDateTime.now());
            repository.save(entity);
            return mapper.map(entity);
        } else {
            throw new AccessDeniedException("not enough rights to update this entity");
        }
    }


    public Page<TReadDto> findAll(Pageable pageable) {
        Page<T> entities = repository.findAll(pageable);
        return entities.map(mapper::map);
    }

    public Page<TReadDto> search(SearchCriteriaDto dto, Pageable pageable) {
        Page<T> entities = repository.findAll(searchMapper.map(dto), pageable);
        return entities.map(mapper::map);
    }

    public Page<TReadDto> findAllByOwner(Pageable pageable) {
        Page<T> entities = repository.findAllByOwnerId(pageable, userService.getCurrentUser().getId());
        return entities.map(mapper::map);
    }

    public Page<TReadDto> findAllByOwner(Pageable pageable, int id) {
        Page<T> entities = repository.findAllByOwnerId(pageable, id);
        return entities.map(mapper::map);
    }

}
