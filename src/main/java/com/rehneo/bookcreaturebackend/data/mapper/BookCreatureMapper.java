package com.rehneo.bookcreaturebackend.data.mapper;
import com.rehneo.bookcreaturebackend.data.dto.create.BookCreatureCreateDto;
import com.rehneo.bookcreaturebackend.data.dto.read.BookCreatureReadDto;
import com.rehneo.bookcreaturebackend.data.dto.read.CoordinatesReadDto;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import com.rehneo.bookcreaturebackend.data.entity.Coordinates;
import com.rehneo.bookcreaturebackend.data.repository.CoordinatesRepository;
import com.rehneo.bookcreaturebackend.error.BadRequestException;
import com.rehneo.bookcreaturebackend.data.repository.MagicCityRepository;
import com.rehneo.bookcreaturebackend.data.repository.RingRepository;
import com.rehneo.bookcreaturebackend.user.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class BookCreatureMapper extends BaseMapper<
        BookCreature,
        Long,
        BookCreatureReadDto,
        BookCreatureCreateDto>{

    private final MagicCityMapper magicCityMapper;
    private final MagicCityRepository magicCityRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final RingRepository ringRepository;
    private final RingMapper ringMapper;

    public BookCreatureMapper(UserMapper userMapper,
                              MagicCityMapper magicCityMapper,
                              MagicCityRepository magicCityRepository,
                              CoordinatesRepository coordinatesRepository,
                              RingRepository ringRepository,
                              RingMapper ringMapper) {
        super(userMapper);
        this.magicCityMapper = magicCityMapper;
        this.magicCityRepository = magicCityRepository;
        this.coordinatesRepository = coordinatesRepository;
        this.ringRepository = ringRepository;
        this.ringMapper = ringMapper;
    }

    @Override
    public BookCreature map(BookCreatureCreateDto createDto) {
        Coordinates coordinates = Coordinates.builder()
                .x(createDto.getCoordinates().getX())
                .y(createDto.getCoordinates().getY())
                .build();
        coordinatesRepository.save(coordinates);
        BookCreature creature = BookCreature.builder()
                .name(createDto.getName())
                .coordinates(coordinates)
                .age(createDto.getAge())
                .type(createDto.getType())
                .location(magicCityRepository.findById(createDto.getLocationId()).orElseThrow(
                        () -> new BadRequestException("Location with id: " + createDto.getLocationId() + " not found")
                ))
                .attackLevel(createDto.getAttackLevel())
                .defenseLevel(createDto.getDefenseLevel())
                .ring(ringRepository.findById(createDto.getRingId()).orElseThrow(
                        () -> new BadRequestException("Ring with id: " + createDto.getRingId() + " not found")
                ))
                .build();
        creature.setEditable(createDto.getEditable());
        return creature;
    }

    @Override
    protected BookCreatureReadDto mapEntity(BookCreature entity) {
        return BookCreatureReadDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .coordinates(CoordinatesReadDto.builder()
                        .x(entity.getCoordinates().getX())
                        .y(entity.getCoordinates().getY())
                        .build())
                .age(entity.getAge())
                .type(entity.getType())
                .location(magicCityMapper.map(entity.getLocation()))
                .attackLevel(entity.getAttackLevel())
                .defenseLevel(entity.getDefenseLevel())
                .ring(ringMapper.map(entity.getRing()))
                .build();
    }

    @Override
    public void update(BookCreature entity, BookCreatureCreateDto createDto) {
        Coordinates coordinates = Coordinates.builder()
                .x(createDto.getCoordinates().getX())
                .y(createDto.getCoordinates().getY())
                .build();
        coordinatesRepository.save(coordinates);
        entity.setName(createDto.getName());
        entity.setCoordinates(coordinates);
        entity.setAge(createDto.getAge());
        entity.setType(createDto.getType());
        entity.setLocation(magicCityRepository.findById(createDto.getLocationId()).orElseThrow(
                () -> new BadRequestException("Location with id: " + createDto.getLocationId() + " not found")
        ));
        entity.setAttackLevel(createDto.getAttackLevel());
        entity.setDefenseLevel(createDto.getDefenseLevel());
        entity.setRing(ringRepository.findById(createDto.getRingId()).orElseThrow(
                () -> new BadRequestException("Ring with id: " + createDto.getRingId() + " not found")
        ));
    }
}
