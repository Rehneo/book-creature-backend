package com.rehneo.bookcreaturebackend.admin;
import com.rehneo.bookcreaturebackend.user.User;
import com.rehneo.bookcreaturebackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRequestMapper {
    private final UserMapper userMapper;
    public AdminRequestReadDto map(AdminRequest adminRequest) {
        AdminRequestReadDto readDto = AdminRequestReadDto.builder()
                .id(adminRequest.getId())
                .user(userMapper.map(adminRequest.getUser()))
                .requestDate(adminRequest.getRequestDate())
                .status(adminRequest.getStatus())
                .approvalDate(adminRequest.getApprovalDate())
                .build();

        User user = adminRequest.getApprovedBy();
        if(user != null){
            readDto.setApprovedBy(userMapper.map(user));
        }
        return readDto;
    }
}
