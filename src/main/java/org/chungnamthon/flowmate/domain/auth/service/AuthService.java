package org.chungnamthon.flowmate.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.auth.service.dto.ReissueTokenServiceRequest;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.global.security.jwt.JwtProvider;
import org.chungnamthon.flowmate.global.security.jwt.dto.TokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse reissue(ReissueTokenServiceRequest request) {
        Member member = memberRepository.findByRefreshToken(request.refreshToken());

        TokenResponse response = jwtProvider.createTokens(member.getId(), member.getRole());
        member.updateRefreshToken(request.refreshToken());

        return response;
    }

}