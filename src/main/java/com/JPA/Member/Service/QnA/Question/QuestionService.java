package com.JPA.Member.Service.QnA.Question;

import com.JPA.Member.Repository.QnA.QuestionRepository;
import com.JPA.Member.Entity.QnA.QuestionEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionEntity> findAll(){
        return questionRepository.findAll();
    }
}
