package com.JPA.Member.Service.QnA.Answer;

import com.JPA.Member.Repository.QnA.Question.QuestionRepository;
import com.JPA.Member.Repository.QnA.Answer.AnswerRepository;
import com.JPA.Member.Entity.QnA.Question.QuestionEntity;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Entity.QnA.Answer.AnswerEntity;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.DTO.QnA.Answer.AnswerDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public void save(Long id, String contents, QuestionEntity question) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        // AnswerEntity 생성 및 저장
        AnswerEntity answerEntity = AnswerEntity.toSaveEntity(memberEntity, contents, question);
        answerRepository.save(answerEntity);

        // 기존 QuestionEntity 가져오기
        QuestionEntity existingQuestion = questionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + question.getId()));

        // 기존 데이터를 유지하고 answerStatus만 업데이트
        existingQuestion.setAnswerStatus("answered");
        questionRepository.save(existingQuestion); // 변경된 데이터만 저장
    }

    public AnswerDTO findByQuestionId(Long id) {
        Optional<AnswerEntity> optionalAnswerEntity = questionRepository.findByQuestionId(id);
        if (optionalAnswerEntity.isPresent()) {
            AnswerEntity answerEntity = optionalAnswerEntity.get();
            return AnswerDTO.toAnswerDTO(answerEntity);
        } else {
            return null;
        }
    }

}
