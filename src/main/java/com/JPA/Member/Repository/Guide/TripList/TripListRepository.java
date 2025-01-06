package com.JPA.Member.Repository.Guide.TripList;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Guide.TripList.TripListEntity;

public interface TripListRepository extends JpaRepository<TripListEntity, Long> {
}
