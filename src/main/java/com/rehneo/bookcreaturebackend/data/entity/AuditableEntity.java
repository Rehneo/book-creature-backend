package com.rehneo.bookcreaturebackend.data.entity;

import com.rehneo.bookcreaturebackend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity<T extends Serializable> extends BaseEntity<T>  {

    @Column(name = "editable", nullable = false)
    private Boolean editable;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name="created_at", nullable=false)
    private ZonedDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name="updated_at")
    private ZonedDateTime updatedAt;

    @PreUpdate
    private void preUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    @PrePersist
    private void prePersist() {
        createdAt = ZonedDateTime.now();
    }
}
