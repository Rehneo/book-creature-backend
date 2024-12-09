package com.rehneo.bookcreaturebackend.data.controller;
import com.rehneo.bookcreaturebackend.data.dto.create.RingCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.RingReadDto;
import com.rehneo.bookcreaturebackend.data.entity.Ring;
import com.rehneo.bookcreaturebackend.data.service.RingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/rings", produces = MediaType.APPLICATION_JSON_VALUE)
public class RingController extends BaseController<
        Ring,
        Long,
        RingReadDto,
        RingCreateDto,
        RingService
        >{
    public RingController(RingService service) {
        super(service);
    }
}
