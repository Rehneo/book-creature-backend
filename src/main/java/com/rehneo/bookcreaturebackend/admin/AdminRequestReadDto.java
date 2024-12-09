package com.rehneo.bookcreaturebackend.admin;
import com.rehneo.bookcreaturebackend.user.UserReadDto;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class AdminRequestReadDto {

    private int id;

    private UserReadDto user;

    private ZonedDateTime requestDate;

    private Status status;
    private UserReadDto approvedBy;

    private ZonedDateTime approvalDate;
}
