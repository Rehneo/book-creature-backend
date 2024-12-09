package com.rehneo.bookcreaturebackend.data.controller;
import com.rehneo.bookcreaturebackend.data.dto.read.AuditableDto;
import com.rehneo.bookcreaturebackend.data.entity.AuditableEntity;
import com.rehneo.bookcreaturebackend.data.search.SearchCriteriaDto;
import com.rehneo.bookcreaturebackend.data.service.BaseService;
import com.rehneo.bookcreaturebackend.error.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.query.SemanticException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.io.Serializable;

@AllArgsConstructor
public abstract class BaseController<
        T extends AuditableEntity<K>,
        K extends Serializable,
        TReadDto extends AuditableDto,
        TCreateDto,
        TService extends BaseService<T,K,TReadDto, TCreateDto, ?, ?>> {

    private final TService service;

    @GetMapping
    public ResponseEntity<Page<TReadDto>> findAll(Pageable pageable) {
        Page<TReadDto> entities = service.findAll(pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(entities.getTotalElements()))
                .body(entities);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<TReadDto>> findAllByOwner(Pageable pageable, @PathVariable int id) {
        Page<TReadDto> entities = service.findAllByOwner(pageable, id);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(entities.getTotalElements()))
                .body(entities);
    }



    @GetMapping("/my")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<TReadDto>> findMy(Pageable pageable) {
        Page<TReadDto> entities = service.findAllByOwner(pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(entities.getTotalElements()))
                .body(entities);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TReadDto>> search(
            @RequestBody(required = false) SearchCriteriaDto request,
            Pageable pageable) {
        Page<TReadDto> entities = service.search(request, pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(entities.getTotalElements()))
                .body(entities);
    }



    @GetMapping("/{id}")
    public ResponseEntity<TReadDto> findById(@PathVariable K id) {
        TReadDto entity = service.findById(id);
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<TReadDto> create(@Valid @RequestBody TCreateDto dto) {
        TReadDto entity = service.create(dto);
        return ResponseEntity.ok(entity);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<TReadDto> update(@PathVariable K id, @Valid @RequestBody TCreateDto dto) {
        TReadDto entity = service.update(id, dto);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Void> delete(@PathVariable K id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BadRequestException.class,
            IllegalArgumentException.class,
            SemanticException.class})
    public ResponseEntity<Response> badRequest(Exception ex){
        Response response = new Response(ex.getMessage( ));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Response> notFound(Exception ex){
        Response response = new Response(ex.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Response> accessDenied(Exception ex){
        Response response = new Response(ex.getMessage());
        return ResponseEntity.status(403).body(response);
    }
}
