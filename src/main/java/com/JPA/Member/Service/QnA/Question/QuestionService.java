package com.JPA.Member.Service.QnA.Question;

import com.JPA.Member.Repository.QnA.QuestionRepository;
import org.springframework.data.domain.PageRequest;
import com.JPA.Member.Entity.QnA.QuestionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import com.JPA.Member.DTO.QnA.QuestionDTO;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionEntity> findAll(){
        return questionRepository.findAll();
    }

    public Page<QuestionDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<QuestionEntity> questionEntities =
                questionRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<QuestionDTO> questionDTOS = questionEntities.map(question ->
                new QuestionDTO(question.getId(), question.getTitle(), question.getMemberEntity().getMemberName(), question.getCreateDate(), question.getAnswerEntity().getAnswerStatus()));
        return questionDTOS;
    }

}