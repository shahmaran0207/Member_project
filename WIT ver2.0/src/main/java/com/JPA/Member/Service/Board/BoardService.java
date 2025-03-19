package com.JPA.Member.Service.Board;

import com.JPA.Member.Repository.Board.BoardFileRepository;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Repository.Board.BoardRepository;
import org.springframework.web.multipart.MultipartFile;
import com.JPA.Member.Entity.Board.BoardFileEntity;
import org.springframework.data.domain.PageRequest;
import com.JPA.Member.Entity.Member.MemberEntity;
import org.springframework.data.domain.Pageable;
import com.JPA.Member.Entity.Board.BoardEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import com.JPA.Member.DTO.Board.BoardDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.io.File;

//DTO -> Entity (Service)
//Entity -> DTO (DTOClass)

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberRepository memberRepository;

    public void save(BoardDTO boardDTO, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        if (boardDTO.getBoardFile().isEmpty()) {
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
            boardRepository.save(boardEntity);
        } else {
            MultipartFile boardFile = boardDTO.getBoardFile();
            String originalFilename = boardFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Member_project/file/" + storedFileName;
            boardFile.transferTo(new File(savePath));

            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO, memberEntity);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity savedBoardEntity = boardRepository.findById(savedId).get();

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(savedBoardEntity, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            return BoardDTO.toBoardDTO(boardEntity);
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO, Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO, memberEntity);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<BoardDTO> boardDTOS = boardEntities.map(board ->
                new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime(), board.getMemberEntity().getMemberName(), board.getLikesCount()));
        return boardDTOS;
    }
}