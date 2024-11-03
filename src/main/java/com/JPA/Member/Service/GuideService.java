package com.JPA.Member.Service;

import com.JPA.Member.Repository.MemberRepository;
import com.JPA.Member.Repository.GuideRepository;
import org.springframework.stereotype.Service;
import com.JPA.Member.Entity.MemberEntity;
import com.JPA.Member.Entity.GuideEntity;
import com.JPA.Member.DTO.MemberDTO;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class GuideService {
    private final GuideRepository guideRepository;
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO, Long id) throws IOException {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

            GuideEntity guideEntity = GuideEntity.toSaveEntity(memberDTO, memberEntity);
            guideRepository.save(guideEntity);
    }
}