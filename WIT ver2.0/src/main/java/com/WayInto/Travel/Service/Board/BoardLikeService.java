package com.WayInto.Travel.Service.Board;

import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Board.BoardLikeRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Repository.Board.BoardRepository;
import com.WayInto.Travel.Entity.Board.BoardLikeEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import com.WayInto.Travel.DTO.Board.BoardLikeDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public String toggleLike(BoardLikeDTO boardLikeDTO) {
        Long boardId = boardLikeDTO.getBoardId();
        Long memberId = boardLikeDTO.getMemberId();

        if (boardId == null || memberId == null) {
            throw new IllegalArgumentException("Board ID and Member ID must not be null");
        }

        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (boardLikeRepository.existsByMemberEntityAndBoardEntity(memberEntity, boardEntity)) {
            boardLikeRepository.deleteByMemberEntityAndBoardEntity(memberEntity, boardEntity);
            boardEntity.decreaseLikesCount();
            boardRepository.save(boardEntity);
            return "Like removed";
        } else {
            BoardLikeEntity like = BoardLikeEntity.toSaveEntity(memberEntity, boardEntity);
            boardLikeRepository.save(like);
            boardEntity.increaseLikesCount();
            boardRepository.save(boardEntity);
            return "Like added";
        }
    }

    public int getLikeCount(Long boardId) {
        return boardLikeRepository.countByBoardEntity(boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID")));
    }

    public boolean isLikedByMember(Long boardId, Long memberId) {
        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return boardLikeRepository.existsByMemberEntityAndBoardEntity(memberEntity, boardEntity);
    }
}
