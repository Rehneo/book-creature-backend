package com.rehneo.bookcreaturebackend.data.search;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SearchSpecificationBuilder<T> {
    private final List<SearchCriteria> params;

    public SearchSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final SearchSpecificationBuilder<T> with(
            @NonNull String key,
            @NonNull Object value
    ) {
        params.add(new SearchCriteria(key, value));
        return this;
    }

    public final SearchSpecificationBuilder<T> with(@NonNull SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty()) return null;

        Specification<T> result = new SearchSpecification<T>(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            SearchCriteria criteria = params.get(i);
            result = Specification.where(result).and(new SearchSpecification<>(criteria));
        }

        return result;
    }

}
