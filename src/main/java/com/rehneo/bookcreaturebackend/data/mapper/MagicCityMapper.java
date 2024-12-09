package com.rehneo.bookcreaturebackend.data.mapper;
import com.rehneo.bookcreaturebackend.data.dto.create.MagicCityCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.MagicCityReadDto;
import com.rehneo.bookcreaturebackend.data.entity.MagicCity;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MagicCityMapper extends BaseMapper<
        MagicCity,
        Integer,
        MagicCityReadDto,
        MagicCityCreateDto> {

    private final UserMapper userMapper;
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
    public MagicCityReadDto map(MagicCity entity) {
        MagicCityReadDto magicCity = MagicCityReadDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .area(entity.getArea())
                .population(entity.getPopulation())
                .establishmentDate(entity.getEstablishmentDate())
                .capital(entity.getCapital())
                .governor(entity.getGovernor())
                .populationDensity(entity.getPopulationDensity())
                .build();
        magicCity.setOwner(userMapper.map(entity.getOwner()));
        magicCity.setCreatedAt(entity.getCreatedAt());
        magicCity.setUpdatedAt(entity.getUpdatedAt());
        User user = entity.getUpdatedBy();
        if(user != null){
            magicCity.setUpdatedBy(userMapper.map(user));
        }
        magicCity.setEditable(entity.getEditable());
        return magicCity;
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
