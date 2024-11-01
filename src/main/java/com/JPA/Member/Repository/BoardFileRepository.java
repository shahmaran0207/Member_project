package com.JPA.Member.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.BoardFileEntity;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}
