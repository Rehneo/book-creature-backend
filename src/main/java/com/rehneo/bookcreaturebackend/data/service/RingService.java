package com.rehneo.bookcreaturebackend.data.service;

import com.rehneo.bookcreaturebackend.data.dto.create.RingCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.RingReadDto;
import com.rehneo.bookcreaturebackend.data.entity.Ring;
import com.rehneo.bookcreaturebackend.data.mapper.RingMapper;
import com.rehneo.bookcreaturebackend.data.repository.RingRepository;
import com.rehneo.bookcreaturebackend.data.search.SearchMapper;
import com.rehneo.bookcreaturebackend.user.UserService;
import org.springframework.stereotype.Service;


@Service
public class RingService extends BaseService<
        Ring,
        Long,
        RingReadDto,
        RingCreateDto,
        RingMapper,
        RingRepository>{
    public RingService(
            RingRepository repository,
            RingMapper mapper,
            UserService userService,
            SearchMapper<Ring> searchMapper) {
        super(repository, mapper, userService, searchMapper);
    }
}
