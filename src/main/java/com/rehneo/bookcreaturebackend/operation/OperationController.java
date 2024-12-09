package com.rehneo.bookcreaturebackend.operation;
import com.rehneo.bookcreaturebackend.data.dto.read.BookCreatureReadDto;
import com.rehneo.bookcreaturebackend.error.AccessDeniedException;
import com.rehneo.bookcreaturebackend.error.ResourceNotFoundException;
import com.rehneo.bookcreaturebackend.error.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/book-creatures/operations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @GetMapping("/avg-attack-level")
    public ResponseEntity<Double> avgAttackLevel(){
        return ResponseEntity.ok(operationService.avgAttackLevel());
    }

    @GetMapping("/filter-by-name-containing")
    public ResponseEntity<Page<BookCreatureReadDto>> findByNameContaining(@RequestParam String value, Pageable pageable){
        Page<BookCreatureReadDto> entities = operationService.findByNameContaining(value, pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(entities.getTotalElements()))
                .body(entities);
    }

    @GetMapping("/filter-by-name-starting-with")
    public ResponseEntity<Page<BookCreatureReadDto>> findByNameStartingWith(@RequestParam String value, Pageable pageable){
        Page<BookCreatureReadDto> entities = operationService.findByNameStartingWith(value, pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(entities.getTotalElements()))
                .body(entities);
    }

    @PatchMapping("/exchange-rings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<BookCreatureReadDto>> exchangeRings(@RequestBody ExchangeRequest request){
        Page<BookCreatureReadDto> entities = operationService.exchangeRings(request.getId1(), request.getId2());
        return ResponseEntity.ok()
                .body(entities);
    }

    @GetMapping("/strongest-ring")
    public ResponseEntity<Page<BookCreatureReadDto>> findByStrongestRing(Pageable pageable){
        Page<BookCreatureReadDto> entities = operationService.findByStrongestRing(pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(entities.getTotalElements()))
                .body(entities);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Response> notFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Response> accessDenied(Exception ex){
        Response response = new Response(ex.getMessage());
        return ResponseEntity.status(403).body(response);
    }

}
