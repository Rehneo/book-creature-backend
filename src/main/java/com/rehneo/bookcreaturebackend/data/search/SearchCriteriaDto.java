package com.rehneo.bookcreaturebackend.data.search;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SearchCriteriaDto {
    @NotNull
    private List<SearchCriteria> list;
}
