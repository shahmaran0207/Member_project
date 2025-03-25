package com.WayInto.Travel.Service.QnA.Question;

import com.WayInto.Travel.Repository.QnA.Question.QuestionRepository;
import com.WayInto.Travel.Entity.QnA.Question.QuestionEntity;
import com.WayInto.Travel.DTO.QnA.Question.QuestionDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

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

}
