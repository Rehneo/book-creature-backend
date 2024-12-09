package com.rehneo.bookcreaturebackend.data.search;
import com.rehneo.bookcreaturebackend.data.entity.*;
import com.rehneo.bookcreaturebackend.user.User;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public class SearchSpecification<T> implements Specification<T> {
    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(
            @NonNull Root<T> root,
            @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder builder) {
        var value = criteria.getValue().toString().toLowerCase();
        switch (criteria.getKey()){
            case "location.id":
                Join<BookCreature, MagicCity> cityJoin = root.join("location");
                return builder.like(
                        builder.lower(cityJoin.<Integer>get("id").as(String.class)),
                        "%" + value + "%");
            case "ring.id":
                Join<BookCreature, Ring> ringJoin = root.join("ring");
                return builder.like(builder.lower(ringJoin.<Integer>get("id").as(String.class)),"%" + value + "%");
            case "coordinates.x":
                Join<BookCreature, Coordinates> coordinatesJoinX = root.join("coordinates");
                return builder.like(
                        builder.lower(coordinatesJoinX.<Float>get("x").as(String.class)),
                        "%" + value + "%");
            case "coordinates.y":
                Join<BookCreature, Coordinates> coordinatesJoinY = root.join("coordinates");
                return builder.like(
                        builder.lower(coordinatesJoinY.<Long>get("y").as(String.class)),
                        "%" + value + "%");
            case "owner.username":
                Join<T, User> ownerJoin = root.join("owner");
                return builder.like(builder.lower(ownerJoin.<String>get("username").as(String.class)), "%" + value + "%");
            case "updatedBy.username":
                Join<T, User> updatedJoin = root.join("updatedBy");
                return builder.like(builder.lower(updatedJoin.<String>get("username").as(String.class)), "%" + value + "%");
            default:
                return builder.like(builder.lower(root.get(criteria.getKey()).as(String.class)), "%" + value + "%");
        }
    }
}
