package com.JPA.Member.Repository.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Board.BoardFileEntity;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}
