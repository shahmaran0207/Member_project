package com.WayInto.Travel.Service.QnA.Question;

import com.WayInto.Travel.Repository.QnA.Question.QuestionFileRepository;
import com.WayInto.Travel.Repository.QnA.Question.QuestionRepository;
import com.WayInto.Travel.Repository.QnA.Answer.AnswerRepository;
import com.WayInto.Travel.Entity.QnA.Question.QuestionFileEntity;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Entity.QnA.Question.QuestionEntity;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import org.springframework.web.multipart.MultipartFile;
import com.WayInto.Travel.DTO.QnA.Question.QuestionDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.WayInto.Travel.Service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final AnswerRepository answerRepository;
    private final QuestionFileRepository questionFileRepository;
    private final ImageService imageService;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    public Page<QuestionDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<QuestionEntity> questionEntities =
                questionRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<QuestionDTO> questionDTOS = questionEntities.map(question ->
                new QuestionDTO(question.getId(), question.getMemberEntity().getId(),question.getTitle(),
                        question.getMemberEntity().getMemberName(), question.getCreateDate(), question.getQuestionhits(),
                        question.getAnswerStatus()));
        return questionDTOS;
    }

    public void save(QuestionDTO questionDTO, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        if (questionDTO.getQuestionFile() == null || questionDTO.getQuestionFile().isEmpty()) {
            QuestionEntity questionEntity = QuestionEntity.toSaveEntity(questionDTO, memberEntity);
            questionRepository.save(questionEntity);
        } else {
            MultipartFile questionFile = questionDTO.getQuestionFile();
            String s3Url = imageService.imageUploadFromFile(questionFile);

            String originalFilename = questionFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            QuestionEntity questionEntity = QuestionEntity.toSaveFileEntity(questionDTO, memberEntity);
            Long savedId = questionRepository.save(questionEntity).getId();
            QuestionEntity savedQuestionEntity = questionRepository.findById(savedId)
                    .orElseThrow(() -> new IllegalStateException("Question entity not found after saving."));

            QuestionFileEntity questionFileEntity = QuestionFileEntity.toQuestionFileEntity(
                    savedQuestionEntity, storedFileName, s3Url);
            questionFileRepository.save(questionFileEntity);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        questionRepository.updateHits(id);
    }

    @Transactional
    public QuestionDTO findById(Long id) {
        Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findById(id);
        if (optionalQuestionEntity.isPresent()) {
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            return QuestionDTO.toQuestionDTO(questionEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id) {
        List<QuestionFileEntity> questionFiles = questionFileRepository.findByQuestionEntity_Id(id);

        if(questionFiles.isEmpty()) {
            answerRepository.deleteByQuestionEntity_Id(id);
            questionRepository.deleteById(id);
        }
        else {
            for (QuestionFileEntity file : questionFiles) {

                imageService.deleteImage(file.getStoredFileName());
                questionFileRepository.delete(file);
            }
            answerRepository.deleteByQuestionEntity_Id(id);
            questionRepository.deleteById(id);
        }
    }
}
