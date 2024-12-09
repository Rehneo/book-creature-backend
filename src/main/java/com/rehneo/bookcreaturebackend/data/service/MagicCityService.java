package com.rehneo.bookcreaturebackend.data.service;


import com.rehneo.bookcreaturebackend.data.dto.create.MagicCityCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.MagicCityReadDto;
import com.rehneo.bookcreaturebackend.data.entity.MagicCity;
import com.rehneo.bookcreaturebackend.data.mapper.MagicCityMapper;
import com.rehneo.bookcreaturebackend.data.repository.MagicCityRepository;
import com.rehneo.bookcreaturebackend.data.search.SearchMapper;
import com.rehneo.bookcreaturebackend.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class MagicCityService extends BaseService<
        MagicCity,
        Integer,
        MagicCityReadDto,
        MagicCityCreateDto,
        MagicCityMapper,
        MagicCityRepository> {

    public MagicCityService(
            MagicCityRepository repository,
            MagicCityMapper mapper,
            UserService userService,
            SearchMapper<MagicCity> searchMapper) {
        super(repository, mapper, userService, searchMapper);
    }
}
