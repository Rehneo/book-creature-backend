package com.rehneo.bookcreaturebackend.data.mapper;
import com.rehneo.bookcreaturebackend.data.dto.read.AuditableDto;
import com.rehneo.bookcreaturebackend.data.entity.AuditableEntity;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserMapper;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@AllArgsConstructor
public abstract class BaseMapper<T extends AuditableEntity<K>,
        K extends Serializable,
        TReadDto extends AuditableDto,
        TCreateDto> {
    private final UserMapper userMapper;


    public abstract T map(TCreateDto createDto);

    public TReadDto map(T entity) {
        TReadDto readDto = mapEntity(entity);
        readDto.setOwner(userMapper.map(entity.getOwner()));
        readDto.setCreatedAt(entity.getCreatedAt());
        readDto.setUpdatedAt(entity.getUpdatedAt());
        User user = entity.getUpdatedBy();
        if(user != null){
            readDto.setUpdatedBy(userMapper.map(user));
        }
        readDto.setEditable(entity.getEditable());
        return readDto;
    }

    public abstract void update(T entity, TCreateDto creatDto);

    protected abstract TReadDto mapEntity(T entity);

}
