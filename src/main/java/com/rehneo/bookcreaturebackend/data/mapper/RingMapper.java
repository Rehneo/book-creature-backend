package com.rehneo.bookcreaturebackend.data.mapper;
import com.rehneo.bookcreaturebackend.data.dto.create.RingCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.RingReadDto;
import com.rehneo.bookcreaturebackend.data.entity.Ring;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RingMapper extends BaseMapper<Ring, Long, RingReadDto, RingCreateDto> {
    private final UserMapper userMapper;

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
    public RingReadDto map(Ring entity) {
        RingReadDto ring = RingReadDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .power(entity.getPower())
                .build();
        ring.setOwner(userMapper.map(entity.getOwner()));
        ring.setCreatedAt(entity.getCreatedAt());
        ring.setUpdatedAt(entity.getUpdatedAt());
        User user = entity.getUpdatedBy();
        if(user != null){
            ring.setUpdatedBy(userMapper.map(user));
        }
        ring.setEditable(entity.getEditable());
        return ring;
    }

    @Override
    public void update(Ring entity, RingCreateDto createDto) {
        entity.setName(createDto.getName());
        entity.setPower(createDto.getPower());
        entity.setEditable(createDto.getEditable());
    }
}
