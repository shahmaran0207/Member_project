package com.WayInto.Travel.Service.Board;

import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Board.BoardHateRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Repository.Board.BoardRepository;
import com.WayInto.Travel.Entity.Board.BoardHateEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import com.WayInto.Travel.DTO.Board.BoardHateDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardHateService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardHateRepository boardHateRepository;

    @Transactional
    public String toggleHate(BoardHateDTO boardHateDTO) {
        Long boardId = boardHateDTO.getBoardId();
        Long memberId = boardHateDTO.getMemberId();

        if (boardId == null || memberId == null) {
            throw new IllegalArgumentException("Board ID and Member ID must not be null");
        }

        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (boardHateRepository.existsByMemberEntityAndBoardEntity(memberEntity, boardEntity)) {
            boardHateRepository.deleteByMemberEntityAndBoardEntity(memberEntity, boardEntity);
            boardEntity.decreaseHatesCount();
            boardRepository.save(boardEntity);
            return "Like removed";
        } else {
            BoardHateEntity hate = BoardHateEntity.toSaveEntity(memberEntity, boardEntity);
            boardHateRepository.save(hate);
            boardEntity.increaseHatesCount();
            boardRepository.save(boardEntity);
            return "Like added";
        }
    }

    public int getHateCount(Long boardId) {
        return boardHateRepository.countByBoardEntity(boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID")));
    }

    public boolean isHatedByMember(Long boardId, Long memberId) {
        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return boardHateRepository.existsByMemberEntityAndBoardEntity(memberEntity, boardEntity);
    }
}