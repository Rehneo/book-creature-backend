package com.rehneo.bookcreaturebackend.operation;
import com.rehneo.bookcreaturebackend.data.dto.read.BookCreatureReadDto;
import com.rehneo.bookcreaturebackend.data.entity.BookCreature;
import com.rehneo.bookcreaturebackend.data.mapper.BookCreatureMapper;
import com.rehneo.bookcreaturebackend.data.repository.BookCreatureRepository;
import com.rehneo.bookcreaturebackend.error.AccessDeniedException;
import com.rehneo.bookcreaturebackend.error.ResourceNotFoundException;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final BookCreatureRepository bookCreatureRepository;
    private final BookCreatureMapper mapper;
    private final UserService userService;

    public double avgAttackLevel(){
        return bookCreatureRepository.avgAttackLevel();
    }


    @Transactional
    public Page<BookCreatureReadDto> exchangeRings(Long myId, Long otherId){
        BookCreature myCreature = bookCreatureRepository.findById(myId).orElseThrow(
                ()-> new ResourceNotFoundException("creature with id: " + myId + " not found")
        );
        BookCreature otherCreature = bookCreatureRepository.findById(otherId).orElseThrow(
                () -> new ResourceNotFoundException("creature with id: " + otherId + " not found")
        );
        User user = userService.getCurrentUser();
        if(
                (myCreature.getOwner().getId() == user.getId() &&
                otherCreature.getOwner().getId() == user.getId()) ||
                ((user.isAdmin()) && (myCreature.getRing().getOwner().getId() == otherCreature.getRing().getOwner().getId()))
        ){
            bookCreatureRepository.exchangeRings(myId, otherId);
            Page<BookCreature> entities = bookCreatureRepository.findByIdIn(PageRequest.of(0,2), myId, otherId);
            return entities.map(mapper::map);
        }else {
            throw new AccessDeniedException("not enough rights to exchange rings");
        }
    }


    public Page<BookCreatureReadDto> findByNameStartingWith(String name, Pageable pageable){
        return bookCreatureRepository.findByNameStartingWith(name, pageable).map(mapper::map);
    }

    public Page<BookCreatureReadDto> findByNameContaining(String name, Pageable pageable){
        return bookCreatureRepository.findByNameContaining(name, pageable).map(mapper::map);
    }

    public Page<BookCreatureReadDto> findByStrongestRing(Pageable pageable){
        return bookCreatureRepository.findByStrongestRing(pageable).map(mapper::map);
    }
}
