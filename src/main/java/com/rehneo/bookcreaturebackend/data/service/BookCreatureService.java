package com.rehneo.bookcreaturebackend.data.service;

import com.rehneo.bookcreaturebackend.concurrent.LockProvider;
import com.rehneo.bookcreaturebackend.data.dto.create.BookCreatureCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.BookCreatureReadDto;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import com.rehneo.bookcreaturebackend.data.mapper.BookCreatureMapper;
import com.rehneo.bookcreaturebackend.data.repository.BookCreatureRepository;
import com.rehneo.bookcreaturebackend.data.search.SearchMapper;
import com.rehneo.bookcreaturebackend.error.AccessDeniedException;
import com.rehneo.bookcreaturebackend.error.ResourceAlreadyExistsException;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                               SearchMapper<BookCreature> searchMapper,
                               LockProvider lockProvider) {
        super(repository, mapper, userService, searchMapper);
        this.lockProvider = lockProvider;
    }

    private final LockProvider lockProvider;

    @Override
    protected void preSave(BookCreature entity, User currentUser) {
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
    @Transactional
    public BookCreatureReadDto create(BookCreatureCreateDto createDto) {
        try {
            lockProvider.getLock().lock();
            if (repository.existsByName(createDto.getName())) {
                throw new ResourceAlreadyExistsException(
                        "creature with name: " + createDto.getName() + " already exists"
                );
            }
            return super.create(createDto);
        } finally {
            lockProvider.getLock().unlock();
        }
    }

    @Override
    @Transactional
    public BookCreatureReadDto update(Long id, BookCreatureCreateDto createDto) {
        try {
            lockProvider.getLock().lock();
            Optional<Long> creatureId = repository.findIdByName(createDto.getName());
            if (creatureId.isPresent()) {
                if (!creatureId.get().equals(id)) {
                    throw new ResourceAlreadyExistsException(
                            "creature with name: " + createDto.getName() + " already exists"
                    );
                }
            }
            return super.update(id, createDto);
        } finally {
            lockProvider.getLock().unlock();
        }
    }
}
