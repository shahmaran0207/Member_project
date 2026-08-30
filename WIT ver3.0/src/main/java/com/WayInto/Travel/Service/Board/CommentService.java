package com.WayInto.Travel.Service.Board;

import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Repository.Board.CommentRepository;
import com.WayInto.Travel.Repository.Board.BoardRepository;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.Entity.Board.CommentEntity;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import com.WayInto.Travel.DTO.Board.CommentDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

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

    public Long save(CommentDTO commentDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(commentDTO.getMemberId());

        if (optionalBoardEntity.isPresent() && optionalMemberEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            MemberEntity memberEntity = optionalMemberEntity.get();

            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity, memberEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }
}
