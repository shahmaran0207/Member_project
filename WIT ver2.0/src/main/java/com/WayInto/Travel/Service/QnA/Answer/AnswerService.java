package com.WayInto.Travel.Service.QnA.Answer;

import com.WayInto.Travel.Repository.QnA.Question.QuestionRepository;
import com.WayInto.Travel.Repository.QnA.Answer.AnswerRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.QnA.Answer.AnswerEntity;
import com.WayInto.Travel.DTO.QnA.Answer.AnswerDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerDTO findByQuestionId(Long id) {
        Optional<AnswerEntity> optionalAnswerEntity = questionRepository.findByQuestionId(id);
        if (optionalAnswerEntity.isPresent()) {
            AnswerEntity answerEntity = optionalAnswerEntity.get();
            return AnswerDTO.toAnswerDTO(answerEntity);
        } else  return null;
    }
}
