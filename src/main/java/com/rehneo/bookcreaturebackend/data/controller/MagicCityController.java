package com.rehneo.bookcreaturebackend.data.controller;
import com.rehneo.bookcreaturebackend.data.dto.create.MagicCityCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.MagicCityReadDto;
import com.rehneo.bookcreaturebackend.data.entity.MagicCity;
import com.rehneo.bookcreaturebackend.data.service.MagicCityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/magic-cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class MagicCityController extends BaseController<
        MagicCity,
        Integer,
        MagicCityReadDto,
        MagicCityCreateDto,
        MagicCityService
        >{
    public MagicCityController(MagicCityService service) {
        super(service);
    }
}
