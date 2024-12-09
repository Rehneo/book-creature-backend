package com.rehneo.bookcreaturebackend.admin;
import com.rehneo.bookcreaturebackend.error.ResourceNotFoundException;
import com.rehneo.bookcreaturebackend.user.Role;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserRepository;
import com.rehneo.bookcreaturebackend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class AdminRequestService {
    private final AdminRequestRepository repository;

    private final AdminRequestMapper mapper;
    private final UserService userService;
    private final UserRepository userRepository;


    public Page<AdminRequestReadDto> findAll(Pageable pageable) {
        return repository.findAllByOrderByRequestDateDesc(pageable).map(mapper::map);
    }

    public AdminRequestReadDto findById(int id) {
        return repository.findById(id).map(mapper::map).orElseThrow(
                () -> new ResourceNotFoundException("request with id: " + id + " not found")
        );
    }

    public Page<AdminRequestReadDto> findAllPending(Pageable pageable) {
        return repository.findAllByStatusOrderByRequestDateDesc(Status.PENDING, pageable).map(mapper::map);
    }

    public Page<AdminRequestReadDto> findAllApproved(Pageable pageable) {
        return repository.findAllByStatusOrderByRequestDateDesc(Status.APPROVED, pageable).map(mapper::map);
    }

    public Page<AdminRequestReadDto> findAllRejected(Pageable pageable) {
        return repository.findAllByStatusOrderByRequestDateDesc(Status.REJECTED, pageable).map(mapper::map);
    }


    public AdminRequestReadDto findByUser(){
        return repository.findByUserId(userService.getCurrentUser().getId()).map(mapper::map).orElse(null);
    }


    @Transactional
    public AdminRequestReadDto create() {
        User user = userService.getCurrentUser();
        if(repository.findByUserId(user.getId()).isPresent()){
            throw new RequestAlreadyExistsException(
                    "User with username: " + user.getUsername() + " has already sent a request"
            );
        }
        AdminRequest adminRequest = AdminRequest.builder()
                .requestDate(ZonedDateTime.now())
                .user(user)
                .status(Status.PENDING)
                .build();
        repository.save(adminRequest);
        return mapper.map(adminRequest);

    }

    @Transactional
    public AdminRequestReadDto process(int id, boolean approved){
        AdminRequest adminRequest = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("request with id: " + id + " not found")
        );
        if(adminRequest.getStatus() != Status.PENDING){
            throw new RequestAlreadyProcessedException("request with id: " + id + " has already been processed");
        }
        adminRequest.setApprovalDate(ZonedDateTime.now());
        adminRequest.setApprovedBy(userService.getCurrentUser());
        if(approved){
            adminRequest.setStatus(Status.APPROVED);
            User user = adminRequest.getUser();
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }else{
            adminRequest.setStatus(Status.REJECTED);
        }
        repository.save(adminRequest);
        return mapper.map(adminRequest);
    }
}
