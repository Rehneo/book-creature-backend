package com.rehneo.bookcreaturebackend.admin;
import com.rehneo.bookcreaturebackend.error.BadRequestException;
import com.rehneo.bookcreaturebackend.error.ResourceNotFoundException;
import com.rehneo.bookcreaturebackend.error.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin-requests", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class    AdminRequestController {
    private final AdminRequestService service;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AdminRequestReadDto>> findAll(Pageable pageable) {
        Page<AdminRequestReadDto> requests = service.findAll(pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(requests.getTotalElements()))
                .body(requests);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminRequestReadDto> findById(@PathVariable int id) {
        AdminRequestReadDto request = service.findById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AdminRequestReadDto>> findAllPending(Pageable pageable) {
        Page<AdminRequestReadDto> requests = service.findAllPending(pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(requests.getTotalElements()))
                .body(requests);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AdminRequestReadDto> findMy(){
        AdminRequestReadDto request = service.findByUser();
        return ResponseEntity.ok(request);
    }

    @GetMapping("/approved")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AdminRequestReadDto>> findAllApproved(Pageable pageable) {
        Page<AdminRequestReadDto> requests = service.findAllApproved(pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(requests.getTotalElements()))
                .body(requests);
    }

    @GetMapping("/rejected")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AdminRequestReadDto>> findAllRejected(Pageable pageable) {
        Page<AdminRequestReadDto> requests = service.findAllRejected(pageable);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(requests.getTotalElements()))
                .body(requests);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AdminRequestReadDto> create(){
        AdminRequestReadDto request = service.create();
        return ResponseEntity.ok().body(request);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminRequestReadDto> process(@PathVariable int id, @RequestParam boolean approved) {
     AdminRequestReadDto request = service.process(id, approved);
     return ResponseEntity.ok().body(request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BadRequestException.class})
    public ResponseEntity<Response> badRequest(Exception ex){
        Response response = new Response(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({RequestAlreadyExistsException.class, RequestAlreadyProcessedException.class})
    public ResponseEntity<Response> conflict(Exception ex){
        Response response = new Response(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Response> notFound(Exception ex){
        Response response = new Response(ex.getMessage());
        return ResponseEntity.status(404).body(response);
    }

}
