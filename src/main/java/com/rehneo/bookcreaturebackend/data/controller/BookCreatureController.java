package com.rehneo.bookcreaturebackend.data.controller;
import com.rehneo.bookcreaturebackend.data.dto.create.BookCreatureCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.BookCreatureReadDto;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import com.rehneo.bookcreaturebackend.data.service.BookCreatureService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/book-creatures", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookCreatureController extends BaseController<
        BookCreature,
        Long,
        BookCreatureReadDto,
        BookCreatureCreateDto,
        BookCreatureService>{



    public BookCreatureController(BookCreatureService service, BookCreatureService bookCreatureService) {
        super(service);
    }

}
