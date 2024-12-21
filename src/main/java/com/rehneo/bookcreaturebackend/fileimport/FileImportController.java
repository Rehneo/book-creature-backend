package com.rehneo.bookcreaturebackend.fileimport;

import com.fasterxml.jackson.core.JsonParseException;
import com.rehneo.bookcreaturebackend.error.ResourceAlreadyExistsException;
import com.rehneo.bookcreaturebackend.error.Response;
import com.rehneo.bookcreaturebackend.fileimport.error.BadFileExtensionException;
import com.rehneo.bookcreaturebackend.fileimport.error.FileIsEmptyException;
import com.rehneo.bookcreaturebackend.fileimport.error.JsonValidationException;
import com.rehneo.bookcreaturebackend.fileimport.error.NamesMustBeUniqueException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/book-creatures/file-imports", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FileImportController {
    private final FileImportService fileImportService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<FileImportDto> importFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileImportDto fileImportDto;
        try {
            fileImportDto = fileImportService.importFile(file);
        }catch (Exception e){
            fileImportService.saveFailedImport();
            throw e;
        }
        return ResponseEntity.ok(fileImportDto);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<FileImportDto>> findAll(Pageable pageable) {
        Page<FileImportDto> fileImports = fileImportService.findAll(pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count",
                String.valueOf(fileImports.getTotalElements())).body(fileImports);
    }


    @ExceptionHandler({FileIsEmptyException.class, BadFileExtensionException.class, JsonParseException.class})
    public ResponseEntity<Response> badRequest(Exception ex) {
        Response response = new Response(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({JsonValidationException.class})
    public ResponseEntity<Response> invalidJson(JsonValidationException ex) {
        Response response = new Response(ex.getMessage(), ex.getErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ResourceAlreadyExistsException.class, NamesMustBeUniqueException.class})
    public ResponseEntity<Response> conflict(Exception ex) {
        Response response = new Response(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<Response> unsupportedMediaType() {
        Response response = new Response("invalid media type");
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }
}
