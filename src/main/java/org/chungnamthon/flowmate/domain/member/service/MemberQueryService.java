package org.chungnamthon.flowmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
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

}