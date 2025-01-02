package com.JPA.Member.Service.QnA.Question;

import com.JPA.Member.Repository.QnA.QuestionRepository;
import org.springframework.data.domain.PageRequest;
import com.JPA.Member.Entity.QnA.QuestionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import com.JPA.Member.DTO.QnA.QuestionDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionEntity> findAll(){
        return questionRepository.findAll();
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

    public Page<QuestionDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<QuestionEntity> questionEntities =
                questionRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<QuestionDTO> questionDTOS = questionEntities.map(question ->
                new QuestionDTO(question.getId(), question.getTitle(), question.getMemberEntity().getMemberName(), question.getCreateDate(), question.getQuestionhits(), question.getAnswerStatus()));
        return questionDTOS;
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    @Transactional
    public void updateHits(Long id) {
        questionRepository.updateHits(id);
    }
}