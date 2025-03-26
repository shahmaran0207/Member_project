package com.WayInto.Travel.Service.Board;

import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import org.springframework.web.multipart.MultipartFile;
import com.WayInto.Travel.Entity.Board.BoardFileEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.data.domain.PageRequest;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import org.springframework.data.domain.Pageable;
import com.WayInto.Travel.Service.ImageService;
import org.springframework.stereotype.Service;
import com.WayInto.Travel.Repository.Board.*;
import com.WayInto.Travel.DTO.Board.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardFileRepository boardFileRepository;
    private final CommentRepository commentRepository;
    private final BoardHateRepository boardHateRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final ImageService imageService;
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

    public void save(BoardDTO boardDTO, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        if (boardDTO.getBoardFile().isEmpty()) {
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
            boardRepository.save(boardEntity);
        } else {
            MultipartFile boardFile = boardDTO.getBoardFile();

            String s3Url = imageService.imageUploadFromFile(boardFile);

            String originalFilename = boardFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO, memberEntity);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity savedBoardEntity = boardRepository.findById(savedId).get();

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(savedBoardEntity, storedFileName, s3Url);
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

    @Transactional
    public void delete(Long id) {
        List<BoardFileEntity> boardFiles = boardFileRepository.findByBoardEntity_Id(id);

        if(boardFiles.isEmpty()) {
            commentRepository.deleteByBoardEntity_Id(id);
            boardRepository.deleteById(id);
        }
        else {
            for (BoardFileEntity file : boardFiles) {
                imageService.deleteImage(file.getStoredFileName());
                boardFileRepository.delete(file);
            }

            boardLikeRepository.deleteByBoardEntity_Id(id);
            boardHateRepository.deleteByBoardEntity_Id(id);
            commentRepository.deleteByBoardEntity_Id(id);
            boardRepository.deleteById(id);
        }
    }

    public BoardDTO update(BoardDTO boardDTO, Long memberId) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        BoardEntity existingBoardEntity = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardDTO.getId()));

        if (boardDTO.getBoardFile().isEmpty()) {
            existingBoardEntity.setBoardPass(boardDTO.getBoardPass());
            existingBoardEntity.setBoardTitle(boardDTO.getBoardTitle());
            existingBoardEntity.setBoardContents(boardDTO.getBoardContents());
            existingBoardEntity.setBoardHits(boardDTO.getBoardHits());
            existingBoardEntity.setLikesCount(boardDTO.getLikesCount());
            existingBoardEntity.setHatesCount(boardDTO.getHatesCount());
            existingBoardEntity.setMemberEntity(memberEntity);
        } else {
            List<BoardFileEntity> existingFiles = boardFileRepository.findByBoardEntity(existingBoardEntity);
            for (BoardFileEntity file : existingFiles) {
                imageService.deleteImage(file.getStoredFileName());
                boardFileRepository.delete(file);
            }

            MultipartFile boardFile = boardDTO.getBoardFile();
            String s3Url = imageService.imageUploadFromFile(boardFile);

            String originalFilename = boardFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            existingBoardEntity.setBoardPass(boardDTO.getBoardPass());
            existingBoardEntity.setFileAttached(1);
            existingBoardEntity.setBoardTitle(boardDTO.getBoardTitle());
            existingBoardEntity.setBoardContents(boardDTO.getBoardContents());
            existingBoardEntity.setBoardHits(boardDTO.getBoardHits());
            existingBoardEntity.setLikesCount(boardDTO.getLikesCount());
            existingBoardEntity.setHatesCount(boardDTO.getHatesCount());
            existingBoardEntity.setMemberEntity(memberEntity);

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(existingBoardEntity, storedFileName, s3Url);
            boardFileRepository.save(boardFileEntity);
        }

        boardRepository.save(existingBoardEntity);
        return findById(boardDTO.getId());
    }
}
