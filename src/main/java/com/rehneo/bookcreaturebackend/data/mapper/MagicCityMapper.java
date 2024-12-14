package com.rehneo.bookcreaturebackend.data.mapper;
import com.rehneo.bookcreaturebackend.data.dto.create.MagicCityCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.MagicCityReadDto;
import com.rehneo.bookcreaturebackend.data.entity.MagicCity;
import com.rehneo.bookcreaturebackend.user.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class MagicCityMapper extends BaseMapper<
        MagicCity,
        Integer,
        MagicCityReadDto,
        MagicCityCreateDto> {

    public MagicCityMapper(UserMapper userMapper) {
        super(userMapper);
    }

    @Override
    public MagicCity map(MagicCityCreateDto magicCityCreateDto) {
        MagicCity magicCity = MagicCity.builder()
                .name(magicCityCreateDto.getName())
                .area(magicCityCreateDto.getArea())
                .population(magicCityCreateDto.getPopulation())
                .establishmentDate(magicCityCreateDto.getEstablishmentDate())
                .capital(magicCityCreateDto.getCapital())
                .governor(magicCityCreateDto.getGovernor())
                .populationDensity(magicCityCreateDto.getPopulationDensity())
                .build();
        magicCity.setEditable(magicCityCreateDto.getEditable());
        return magicCity;
    }

    @Override
    protected MagicCityReadDto mapEntity(MagicCity entity) {
        return MagicCityReadDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .area(entity.getArea())
                .population(entity.getPopulation())
                .establishmentDate(entity.getEstablishmentDate())
                .capital(entity.getCapital())
                .governor(entity.getGovernor())
                .populationDensity(entity.getPopulationDensity())
                .build();
    }

    @Override
    public void update(MagicCity entity, MagicCityCreateDto createDto) {
        entity.setName(createDto.getName());
        entity.setArea(createDto.getArea());
        entity.setPopulation(createDto.getPopulation());
        entity.setEstablishmentDate(createDto.getEstablishmentDate());
        entity.setCapital(createDto.getCapital());
        entity.setGovernor(createDto.getGovernor());
        entity.setPopulationDensity(createDto.getPopulationDensity());
        entity.setEditable(createDto.getEditable());
    }
}
