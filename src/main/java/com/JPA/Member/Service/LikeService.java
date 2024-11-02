package com.JPA.Member.Service;

import com.JPA.Member.DTO.LikeDTO;
import com.JPA.Member.Entity.BoardEntity;
import com.JPA.Member.Entity.LikeEntity;
import com.JPA.Member.Entity.MemberEntity;
import com.JPA.Member.Repository.BoardRepository;
import com.JPA.Member.Repository.LikeRepository;
import com.JPA.Member.Repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return "Like removed";
        } else {
            LikeEntity like = LikeEntity.toSaveEntity(memberEntity, boardEntity);
            likeRepository.save(like);
            return "Like added";
        }
    }

    public int getLikeCount(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        return likeRepository.countByBoardEntity(board);
    }
}
