package org.chungnamthon.flowmate.domain.challengeapplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.chungnamthon.flowmate.domain.challenge.entity.ChallengeFixture;
import org.chungnamthon.flowmate.domain.challenge.repository.ChallengeRepository;
import org.chungnamthon.flowmate.domain.challengeapplication.repository.ChallengeApplicationRepository;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationCreateServiceRequest;
import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.global.exception.DuplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
record ChallengeApplicationCommandServiceTest(
        ChallengeApplicationRepository challengeApplicationRepository,
        MemberRepository memberRepository,
        ChallengeRepository challengeRepository,
        ChallengeApplicationCommandService challengeApplicationCommandService
) {

    @DisplayName("챌린지 신청을 한다.")
    @Test
    void apply() {
        var member = MemberFixture.createMember();
        memberRepository.save(member);

        var challenge = ChallengeFixture.createChallenge();
        challengeRepository.save(challenge);

        var request = ChallengeApplicationCreateServiceRequest.builder()
                .memberId(member.getId())
                .challengeId(challenge.getId())
                .message("20글자가 넘는 유효한 테스트 메세지입니다")
                .build();
        // 신청 전
        boolean isNotExisted = challengeApplicationRepository.existsByMemberIdAndChallengeId(member.getId(), challenge.getId());
        assertThat(isNotExisted).isFalse();

        challengeApplicationCommandService.apply(request);
        // 신청 후
        boolean isExisted = challengeApplicationRepository.existsByMemberIdAndChallengeId(member.getId(), challenge.getId());
        assertThat(isExisted).isTrue();
    }

    @DisplayName("중복 신청으로 챌린지 신청에 실패한다.")
    @Test
    void failApply() {
        var member = MemberFixture.createMember();
        memberRepository.save(member);

        var challenge = ChallengeFixture.createChallenge();
        challengeRepository.save(challenge);

        var request = ChallengeApplicationCreateServiceRequest.builder()
                .memberId(member.getId())
                .challengeId(challenge.getId())
                .message("20글자가 넘는 유효한 테스트 메세지입니다")
                .build();
        // 중복 신청을 위해 미리 신청
        challengeApplicationCommandService.apply(request);

        assertThatThrownBy(() -> challengeApplicationCommandService.apply(request))
            .isInstanceOf(DuplicationException.class);
    }

}