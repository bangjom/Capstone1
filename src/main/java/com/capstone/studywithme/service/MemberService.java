package com.capstone.studywithme.service;

import com.capstone.studywithme.Exception.PasswordWrongException;
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

    @Transactional
    public Member authenticate(String email, String password){

        List<Member> findMembers = memberRepository.findByEmail(email);
        if(findMembers.isEmpty()){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        if(!passwordEncoder.matches(password,findMembers.get(0).getPassword())){
            throw new PasswordWrongException();
        }
        return findMembers.get(0);
    }
    private void validateDuplicateMember(Member member){

        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    public List<Member> findMembers(){return memberRepository.findAll();}

    public Member findOne(Long memberId){return memberRepository.findOne(memberId);}

    public Member findOneByEmail(String email){return memberRepository.findByEmail(email).get(0);}

    @Transactional
    public void update(Long id, String email) {
        Member member = memberRepository.findOne(id);
        member.setEmail(email);
        memberRepository.save(member);
    }

    @Transactional
    public void updateCoin(String email, Long coin){
        Member uMember = memberRepository.findOneByEmail(email);
        uMember.setCoin(uMember.getCoin()-coin);
        memberRepository.save(uMember);
    }

}
