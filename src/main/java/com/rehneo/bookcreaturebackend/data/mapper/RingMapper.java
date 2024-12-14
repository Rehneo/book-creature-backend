package com.rehneo.bookcreaturebackend.data.mapper;
import com.rehneo.bookcreaturebackend.data.dto.create.RingCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.RingReadDto;
import com.rehneo.bookcreaturebackend.data.entity.Ring;
import com.rehneo.bookcreaturebackend.user.UserMapper;
import org.springframework.stereotype.Service;


@Service
public class RingMapper extends BaseMapper<Ring, Long, RingReadDto, RingCreateDto> {

    public RingMapper(UserMapper userMapper) {
        super(userMapper);
    }

    @Override
    public Ring map(RingCreateDto ringCreateDto) {
        Ring ring = Ring.builder()
                .name(ringCreateDto.getName())
                .power(ringCreateDto.getPower())
                .build();
        ring.setEditable(ringCreateDto.getEditable());
        return ring;
    }
    @Override
    protected RingReadDto mapEntity(Ring entity) {
        return RingReadDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .power(entity.getPower())
                .build();
    }

    @Override
    public void update(Ring entity, RingCreateDto createDto) {
        entity.setName(createDto.getName());
        entity.setPower(createDto.getPower());
        entity.setEditable(createDto.getEditable());
    }
}
