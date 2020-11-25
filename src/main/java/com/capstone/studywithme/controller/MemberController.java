package com.capstone.studywithme.controller;

import com.capstone.studywithme.domain.Member;
import com.capstone.studywithme.service.MemberService;
import com.capstone.studywithme.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JwtUtil jwtUtil;

    @PostMapping("/account/join")
    public CreateMemberResponse signup(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setCreated_at(LocalDateTime.now());
        member.setUpdated_at(LocalDateTime.now());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/account/log-in")
    public SearchMemberResponse login(@RequestBody @Valid SearchMemberRequest request) throws URISyntaxException {
        Member findmember = memberService.authenticate(request.getEmail(), request.getPassword());
        String accessToken = jwtUtil.createToken(findmember.getId(), findmember.getEmail());
        return new SearchMemberResponse(accessToken);
    }

    @GetMapping("/members")
    public Result searchMember(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getEmail()))
                .collect(Collectors.toList());
        return new Result(collect.size(),collect);
    }

    @PutMapping("/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getEmail());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getEmail());
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String email;
    }


    @Data
    static class CreateMemberRequest {

        @NotEmpty
        private String email;

        @NotEmpty
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @Data
    static class UpdateMemberRequest{
        private String email;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String email;
    }

    @Data
    @AllArgsConstructor
    static class SearchMemberRequest {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class SearchMemberResponse{
        private String accessToken;
    }

}
