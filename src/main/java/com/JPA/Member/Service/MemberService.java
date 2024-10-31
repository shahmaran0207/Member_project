package com.JPA.Member.Service;

import com.JPA.Member.Repository.MemberRepository;
import org.springframework.stereotype.Service;
import com.JPA.Member.Entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import com.JPA.Member.DTO.MemberDTO;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository mr;
    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        mr.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberdto) {
        Optional<MemberEntity> bymemberemail = mr.findByMemberEmail(memberdto.getMemberEmail());

        if(bymemberemail.isPresent()) {
            MemberEntity memberEntity = bymemberemail.get();

            if(memberEntity.getMemberPassword().equals(memberdto.getMemberPassword())) {
                MemberDTO dto= MemberDTO.toMemberDTO(memberEntity);
                return dto;

            } else return null;
        } else return null;
    }

    //Repository는 Entity로 주고받음
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDTO> dtoList = new ArrayList<>();    //DTO 객체를 위한 리스트

        for(MemberEntity memberEntity : memberEntityList) {
            dtoList.add(MemberDTO.toMemberDTO(memberEntity));
        }

        return dtoList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = mr.findById(id);

        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else{
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = mr.findByMemberEmail(myEmail);

        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else{
            return null;
        }
    }

    public void update(MemberDTO memberdto) {
        mr.save(MemberEntity.toUpdateMemberEntity(memberdto));
    }
}