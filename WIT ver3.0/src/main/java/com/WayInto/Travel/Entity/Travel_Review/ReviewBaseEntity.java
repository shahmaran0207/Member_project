package com.WayInto.Travel.Entity.Travel_Review;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class ReviewBaseEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

     @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedTime;
}