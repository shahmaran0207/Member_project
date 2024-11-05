package com.JPA.Member.Service.Board;

import org.springframework.transaction.annotation.Transactional;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Repository.Board.BoardRepository;
import com.JPA.Member.Repository.Board.LikeRepository;
import jakarta.persistence.EntityNotFoundException;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.Entity.Board.BoardEntity;
import com.JPA.Member.Entity.Board.LikeEntity;
import org.springframework.stereotype.Service;
import com.JPA.Member.DTO.Board.LikeDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public String toggleLike(LikeDTO likeDTO) {
        Long boardId = likeDTO.getBoardId();
        Long memberId = likeDTO.getMemberId();

        if (boardId == null || memberId == null) {
            throw new IllegalArgumentException("Board ID and Member ID must not be null");
        }

        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (likeRepository.existsByMemberEntityAndBoardEntity(memberEntity, boardEntity)) {
            likeRepository.deleteByMemberEntityAndBoardEntity(memberEntity, boardEntity);
            boardEntity.decreaseLikesCount();
            boardRepository.save(boardEntity);
            return "Like removed";
        } else {
            LikeEntity like = LikeEntity.toSaveEntity(memberEntity, boardEntity);
            likeRepository.save(like);
            boardEntity.increaseLikesCount();
            boardRepository.save(boardEntity);
            return "Like added";
        }
    }

    public int getLikeCount(Long boardId) {
        return likeRepository.countByBoardEntity(boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID")));
    }

    public boolean isLikedByMember(Long boardId, Long memberId) {
        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + boardId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return likeRepository.existsByMemberEntityAndBoardEntity(memberEntity, boardEntity);
    }
}
