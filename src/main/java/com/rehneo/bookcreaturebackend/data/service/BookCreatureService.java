package com.rehneo.bookcreaturebackend.data.service;

import com.rehneo.bookcreaturebackend.data.dto.create.BookCreatureCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.BookCreatureReadDto;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import com.rehneo.bookcreaturebackend.data.mapper.BookCreatureMapper;
import com.rehneo.bookcreaturebackend.data.repository.BookCreatureRepository;
import com.rehneo.bookcreaturebackend.data.search.SearchMapper;
import com.rehneo.bookcreaturebackend.error.AccessDeniedException;
import com.rehneo.bookcreaturebackend.error.ResourceAlreadyExistsException;
import com.rehneo.bookcreaturebackend.error.ResourceNotFoundException;
import com.rehneo.bookcreaturebackend.user.Role;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class BookCreatureService extends BaseService
        <
                BookCreature,
                Long, BookCreatureReadDto,
                BookCreatureCreateDto,
                BookCreatureMapper,
                BookCreatureRepository> {

    public BookCreatureService(BookCreatureRepository repository,
                               BookCreatureMapper mapper, UserService userService,
                               SearchMapper<BookCreature> searchMapper) {
        super(repository, mapper, userService, searchMapper);
    }

    private void preSave(BookCreature entity) {
        User locationOwner = entity.getLocation().getOwner();
        User ringOwner = entity.getRing().getOwner();
        if (locationOwner.getId() != entity.getOwner().getId()) {
            throw new AccessDeniedException(
                    "not enough rights to use the Location with id: " + entity.getLocation().getId()
            );
        }
        if (ringOwner.getId() != entity.getOwner().getId()) {
            throw new AccessDeniedException(
                    "not enough rights to use the Ring with id: " + entity.getLocation().getId()
            );
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BookCreatureReadDto create(BookCreatureCreateDto createDto) {
        BookCreature entity = mapper.map(createDto);
        User user = userService.getCurrentUser();
        entity.setOwner(user);
        entity.setCreatedAt(ZonedDateTime.now());
        preSave(entity);
        if (repository.existsByName(createDto.getName())) {
            throw new ResourceAlreadyExistsException(
                    "creature with name: " + createDto.getName() + " already exists"
            );
        }
        repository.save(entity);
        return mapper.map(entity);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BookCreatureReadDto update(Long id, BookCreatureCreateDto createDto) {
        BookCreature entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found: " + id)
        );
        User user = userService.getCurrentUser();
        if (
                (user.getRole() == Role.ADMIN && entity.getEditable()) ||
                        user.getId() == entity.getOwner().getId()) {
            mapper.update(entity, createDto);
            entity.setUpdatedBy(userService.getCurrentUser());
            entity.setUpdatedAt(ZonedDateTime.now());
            preSave(entity);
            Optional<Long> creatureId = repository.findIdByName(createDto.getName());
            if (creatureId.isPresent()) {
                if (!creatureId.get().equals(id)) {
                    throw new ResourceAlreadyExistsException(
                            "creature with name: " + createDto.getName() + " already exists"
                    );
                }
            }
            repository.save(entity);
            return mapper.map(entity);
        } else {
            throw new AccessDeniedException("not enough rights to update this entity");
        }
    }
}
