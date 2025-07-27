package org.chungnamthon.flowmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

}