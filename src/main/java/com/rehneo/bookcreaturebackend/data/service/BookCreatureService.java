package com.rehneo.bookcreaturebackend.data.service;
import com.rehneo.bookcreaturebackend.data.dto.create.BookCreatureCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.BookCreatureReadDto;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import com.rehneo.bookcreaturebackend.data.mapper.BookCreatureMapper;
import com.rehneo.bookcreaturebackend.data.repository.BookCreatureRepository;
import com.rehneo.bookcreaturebackend.data.search.SearchMapper;
import com.rehneo.bookcreaturebackend.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class BookCreatureService extends BaseService<
        BookCreature,
        Long,
        BookCreatureReadDto,
        BookCreatureCreateDto,
        BookCreatureMapper,
        BookCreatureRepository>{

    public BookCreatureService(
            BookCreatureRepository repository,
            BookCreatureMapper mapper,
            UserService userService,
            SearchMapper<BookCreature> searchMapper) {
        super(repository, mapper, userService, searchMapper);
    }


}
