package com.rehneo.bookcreaturebackend.data.service;
import com.rehneo.bookcreaturebackend.data.dto.create.BookCreatureCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.BookCreatureReadDto;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import com.rehneo.bookcreaturebackend.data.mapper.BookCreatureMapper;
import com.rehneo.bookcreaturebackend.data.repository.BookCreatureRepository;
import com.rehneo.bookcreaturebackend.data.search.SearchMapper;
import com.rehneo.bookcreaturebackend.error.AccessDeniedException;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class BookCreatureService extends BaseService<
        BookCreature,
        Long,
        BookCreatureReadDto,
        BookCreatureCreateDto,
        BookCreatureMapper,
        BookCreatureRepository> {

    public BookCreatureService(
            BookCreatureRepository repository,
            BookCreatureMapper mapper,
            UserService userService,
            SearchMapper<BookCreature> searchMapper) {
        super(repository, mapper, userService, searchMapper);
    }

    @Override
    protected void preSave(BookCreature entity, User currentUser) {
        User locationOwner = entity.getLocation().getOwner();
        User ringOwner = entity.getRing().getOwner();
        if(
                locationOwner.getId() != entity.getOwner().getId()
        ) {
            throw new AccessDeniedException("not enough rights to use the Location with id: " + entity.getLocation().getId());
        }
        if(
                ringOwner.getId() != entity.getOwner().getId()
        ) {
            throw new AccessDeniedException("not enough rights to use the Ring with id: " + entity.getLocation().getId());
        }
    }
}
