package org.chungnamthon.flowmate.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import org.chungnamthon.flowmate.domain.auth.service.dto.ReissueTokenServiceRequest;
import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.global.exception.NotFoundException;
import org.chungnamthon.flowmate.global.security.jwt.JwtProvider;
import org.chungnamthon.flowmate.global.security.jwt.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
record AuthServiceTest(
        MemberRepository memberRepository,
        JwtProvider jwtProvider,
        AuthService authService,
        EntityManager entityManager
) {

    @DisplayName("AT와 RT를 재발급한다.")
    @Test
    void reissue() {
        var member = MemberFixture.createMember();
        memberRepository.save(member);
        entityManager.flush();
        // member refreshToken 등록
        var tokenResponse = jwtProvider.createTokens(member.getId(), member.getRole());
        member.updateRefreshToken(tokenResponse.refreshToken());

        var request = new ReissueTokenServiceRequest(member.getRefreshToken());

        TokenResponse result = authService.reissue(request);

        assertThat(result.accessToken()).isNotNull();
        assertThat(result.refreshToken()).isNotNull();
    }

    @DisplayName("요청 RT와 회원 RT가 다를 경우 에러를 반환한다.")
    @Test
    void failReissue() {
        var member = MemberFixture.createMember();
        memberRepository.save(member);
        entityManager.flush();
        // member refreshToken 등록
        var tokenResponse = jwtProvider.createTokens(member.getId(), member.getRole());
        member.updateRefreshToken(tokenResponse.refreshToken());

        var request = new ReissueTokenServiceRequest("invalid-refresh-token");

        assertThatThrownBy(() -> authService.reissue(request))
            .isInstanceOf(NotFoundException.class);
    }

}