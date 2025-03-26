package com.WayInto.Travel.DTO.Board;

import com.WayInto.Travel.DTO.Member.MemberDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.web.multipart.MultipartFile;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import java.time.LocalDateTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BoardDTO {

    private Long id;

    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private String originalFileName;
    private String storedFileName;
    private String memberName;

    private int boardHits;
    private int fileAttached;
    private int likesCount;
    private int hatesCount;

    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    private MultipartFile boardFile;

    public BoardDTO(Long id, String boardTitle, int boardHits, LocalDateTime boardCreatedTime, String memberName, int likesCount) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        this.memberName = memberName;
        this.likesCount =likesCount;
    }

    private String convertS3Url(String storedFileName) {
        String region = "ap-northeast-2";
        String bucketName = "www.witwit.com";

        if (storedFileName.startsWith("https://")) {
            storedFileName = storedFileName.substring(storedFileName.lastIndexOf("/") + 1);
        }

        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + storedFileName;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        boardDTO.setLikesCount((boardEntity.getLikesCount()));
        boardDTO.setHatesCount(boardEntity.getHatesCount());

        boardDTO.setMemberName(boardEntity.getMemberEntity().getMemberName());

        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
        } else {

            boardDTO.setFileAttached(boardEntity.getFileAttached());
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());

            String storedFileName = boardEntity.getBoardFileEntityList().get(0).getStoredFileName();

            storedFileName = boardDTO.convertS3Url(storedFileName);
            boardDTO.setStoredFileName(storedFileName);

        }

        return boardDTO;
    }

}