package com.rehneo.bookcreaturebackend.fileimport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;
import com.rehneo.bookcreaturebackend.concurrent.LockProvider;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import com.rehneo.bookcreaturebackend.data.repository.BookCreatureRepository;
import com.rehneo.bookcreaturebackend.data.repository.CoordinatesRepository;
import com.rehneo.bookcreaturebackend.data.repository.MagicCityRepository;
import com.rehneo.bookcreaturebackend.data.repository.RingRepository;
import com.rehneo.bookcreaturebackend.error.ResourceAlreadyExistsException;
import com.rehneo.bookcreaturebackend.fileimport.error.BadFileExtensionException;
import com.rehneo.bookcreaturebackend.fileimport.error.FileIsEmptyException;
import com.rehneo.bookcreaturebackend.fileimport.error.JsonValidationException;
import com.rehneo.bookcreaturebackend.fileimport.error.NamesMustBeUniqueException;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FileImportService {

    private final FileImportRepository fileImportRepository;
    private final FileImportMapper fileImportMapper;
    private final BookCreatureRepository bookCreatureRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final RingRepository ringRepository;
    private final MagicCityRepository magicCityRepository;
    private final LockProvider lockProvider;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private static final String SCHEMA_VALIDATION_FILE = "/schemas/book-creature.json";

    @Transactional
    public FileImportDto importFile(MultipartFile file) throws IOException {
        try {
            List<BookCreature> creatures = processFile(file);
            Set<String> names = creatures.stream().map(BookCreature::getName).collect(Collectors.toSet());
            if (names.size() != creatures.size()) {
                throw new NamesMustBeUniqueException("names of the creatures must be unique");
            }
            try {
                lockProvider.getLock().lock();
                User currentUser = userService.getCurrentUser();
                for (BookCreature creature : creatures) {
                    if (bookCreatureRepository.existsByName(creature.getName())) {
                        throw new ResourceAlreadyExistsException(
                                "Creature with name " + creature.getName() + " already exists"
                        );
                    }
                    creature.setCreatedAt(ZonedDateTime.now());
                    creature.setOwner(currentUser);
                    creature.getLocation().setOwner(currentUser);
                    creature.getLocation().setCreatedAt(ZonedDateTime.now());
                    creature.getRing().setOwner(currentUser);
                    creature.getRing().setCreatedAt(ZonedDateTime.now());
                    coordinatesRepository.save(creature.getCoordinates());
                    ringRepository.save(creature.getRing());
                    magicCityRepository.save(creature.getLocation());
                    bookCreatureRepository.save(creature);
                }
            } finally {
                lockProvider.getLock().unlock();
            }
            FileImport fileImport = FileImport.builder()
                    .user(userService.getCurrentUser())
                    .createdAt(ZonedDateTime.now())
                    .status(Status.SUCCESS)
                    .addedCount(creatures.size())
                    .build();
            fileImportRepository.save(fileImport);
            return fileImportMapper.map(fileImport);
        } catch (Exception e) {
            fileImportRepository.save(FileImport.builder()
                    .createdAt(ZonedDateTime.now())
                    .status(Status.FAILED)
                    .user(userService.getCurrentUser())
                    .addedCount(0)
                    .build());
            throw e;
        }
    }


    private List<BookCreature> processFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FileIsEmptyException("File is empty");
        }

        var fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (fileExtension == null || !Objects.equals(fileExtension.toLowerCase(), "json")) {
            throw new BadFileExtensionException("File extension must be .json");
        }

        SchemaValidatorsConfig config = SchemaValidatorsConfig.builder()
                .pathType(PathType.LEGACY)
                .errorMessageKeyword("errorMessage")
                .nullableKeywordEnabled(true)
                .typeLoose(false)
                .build();
        JsonSchema schema = JsonSchemaFactory
                .getInstance(SpecVersion.VersionFlag.V7)
                .getSchema(getClass().getResourceAsStream(SCHEMA_VALIDATION_FILE), config);
        Set<ValidationMessage> errors = schema.validate(objectMapper.readTree(file.getInputStream()));
        if (!errors.isEmpty()) {
            List<String> errorMessages = errors.stream().map(ValidationMessage::getMessage).toList();
            throw new JsonValidationException("Invalid json data", errorMessages);
        }

        return objectMapper.readValue(file.getInputStream(), new TypeReference<>() {
        });
    }


    public Page<FileImportDto> findAll(Pageable pageable) {

        User currentUser = userService.getCurrentUser();

        Page<FileImport> pages;
        if (currentUser.isAdmin()) {
            pages = fileImportRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            pages = fileImportRepository.findAllByUserOrderByCreatedAtDesc(currentUser, pageable);
        }
        return pages.map(fileImportMapper::map);
    }

}
