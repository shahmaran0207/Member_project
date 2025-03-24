package com.WayInto.Travel.Service.Board;

import com.WayInto.Travel.Repository.Board.BoardRepository;
import org.springframework.data.domain.PageRequest;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.WayInto.Travel.DTO.Board.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<BoardDTO> boardDTOS = boardEntities.map(board ->
                new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime(),
                        board.getMemberEntity().getMemberName(), board.getLikesCount()));
        return boardDTOS;
    }
}
