package org.chungnamthon.flowmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.domain.member.service.dto.MemberProfileResponse;
import org.chungnamthon.flowmate.global.exception.DuplicationException;
import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public void checkNameDuplicate(String name) {
        if (memberRepository.existsByName(name)) {
            throw new DuplicationException(ErrorStatus.DUPLICATE_NICKNAME);
        }
    }

    public MemberProfileResponse getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId);

        return MemberProfileResponse.builder()
                .name (member.getName())
                .image(member.getProfileImgUrl())
                .grade(member.getGrade())
                .point(member.getPoint())
                .build();
    }

}