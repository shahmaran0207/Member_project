package com.WayInto.Travel.DTO.QnA.Question;

import com.WayInto.Travel.Entity.QnA.Question.QuestionEntity;
import com.WayInto.Travel.Entity.QnA.Answer.AnswerEntity;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionDTO {

    private Long id;
    private Long memberId;

    private int questionhits;
    private int fileAttached;

    private String title;
    private String content;
    private String questionPass;
    private String memberName;
    private String answerStatus;
    private String originalFileName;
    private String storedFileName;

    private LocalDateTime createDate;

    private MultipartFile QuestionFile;

    private List<AnswerEntity> answersList = new ArrayList<>();

    public QuestionDTO(Long id, Long memberId, String title, String memberName, LocalDateTime createDate, int questionhits, String answerStatus) {
        this.id = id;
        this.title = title;
        this.memberId = memberId;
        this.memberName = memberName;
        this.createDate = createDate;
        this.questionhits = questionhits;
        this.answerStatus = answerStatus;
    }

    private String convertS3Url(String storedFileName) {
        String region = "ap-northeast-2";
        String bucketName = "www.witwit.com";

        if (storedFileName.startsWith("https://")) {
            storedFileName = storedFileName.substring(storedFileName.lastIndexOf("/") + 1);
        }

        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + storedFileName;
    }

    public static QuestionDTO toQuestionDTO(QuestionEntity questionEntity) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(questionEntity.getId());
        questionDTO.setTitle(questionEntity.getTitle());
        questionDTO.setQuestionPass(questionEntity.getQuestionPass());
        questionDTO.setContent(questionEntity.getContent());
        questionDTO.setMemberId(questionEntity.getMemberEntity().getId());
        questionDTO.setMemberName(questionEntity.getMemberEntity().getMemberName());
        questionDTO.setCreateDate(questionEntity.getCreateDate());
        questionDTO.setQuestionhits(questionEntity.getQuestionhits());
        questionDTO.setAnswerStatus(questionEntity.getAnswerStatus());

        if (questionEntity.getFileAttached() == 0) {
            questionDTO.setFileAttached(questionEntity.getFileAttached());
        } else {
            questionDTO.setFileAttached(questionEntity.getFileAttached());
            questionDTO.setOriginalFileName(questionEntity.getQuestionFileEntityList().get(0).getOriginalFileName());
            questionDTO.setStoredFileName(questionEntity.getQuestionFileEntityList().get(0).getStoredFileName());

            questionDTO.setFileAttached(questionEntity.getFileAttached());
            questionDTO.setOriginalFileName(questionEntity.getQuestionFileEntityList().get(0).getOriginalFileName());

            String storedFileName = questionEntity.getQuestionFileEntityList().get(0).getStoredFileName();

            storedFileName = questionDTO.convertS3Url(storedFileName);
            questionDTO.setStoredFileName(storedFileName);
        }

        return questionDTO;
    }

    public static QuestionEntity toEntity(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(questionDTO.getId());
        return questionEntity;
    }
}