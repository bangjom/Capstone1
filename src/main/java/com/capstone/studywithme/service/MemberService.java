package com.capstone.studywithme.service;

import com.capstone.studywithme.domain.Member;
import com.capstone.studywithme.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){

        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){return memberRepository.findAll();}

    public Member findOne(Long memberId){return memberRepository.findOne(memberId);}

    @Transactional
    public void update(Long id, String email) {
        Member member = memberRepository.findOne(id);
        member.setEmail(email);
    }
}
