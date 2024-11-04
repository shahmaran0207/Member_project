package com.JPA.Member.Service.Board;

import com.JPA.Member.Entity.MemberEntity;
import com.JPA.Member.Repository.Board.CommentRepository;
import com.JPA.Member.Repository.Board.BoardRepository;
import com.JPA.Member.Repository.MemberRepository;
import org.springframework.stereotype.Service;
import com.JPA.Member.Entity.Board.CommentEntity;
import com.JPA.Member.Entity.Board.BoardEntity;
import lombok.RequiredArgsConstructor;
import com.JPA.Member.DTO.Board.CommentDTO;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long save(CommentDTO commentDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(commentDTO.getBoardId());

        if (optionalBoardEntity.isPresent() && optionalMemberEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            MemberEntity memberEntity = optionalMemberEntity.get();

            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity, memberEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

}