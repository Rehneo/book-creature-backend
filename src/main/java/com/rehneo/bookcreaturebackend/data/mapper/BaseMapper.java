package com.rehneo.bookcreaturebackend.data.mapper;
import com.rehneo.bookcreaturebackend.data.dto.read.AuditableDto;
import com.rehneo.bookcreaturebackend.data.entity.AuditableEntity;

import java.io.Serializable;


public abstract class BaseMapper<T extends AuditableEntity<K>,
        K extends Serializable,
        TReadDto extends AuditableDto,
        TCreateDto> {

    public abstract T map(TCreateDto createDto);

    public abstract TReadDto map(T entity);

    public abstract void update(T entity, TCreateDto creatDto);

}
