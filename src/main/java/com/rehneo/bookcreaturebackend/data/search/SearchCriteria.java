package com.rehneo.bookcreaturebackend.data.search;


import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private Object value;

    public SearchCriteria(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
