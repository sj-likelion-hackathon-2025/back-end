package org.chungnamthon.flowmate.domain.challengeapplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.chungnamthon.flowmate.domain.challengeapplication.entity.ApplicationStatus.APPROVED;
import static org.chungnamthon.flowmate.domain.challengeapplication.entity.ApplicationStatus.PENDING;

import org.chungnamthon.flowmate.domain.challenge.entity.ChallengeFixture;
import org.chungnamthon.flowmate.domain.challenge.repository.ChallengeRepository;
import org.chungnamthon.flowmate.domain.challengeapplication.entity.ChallengeApplication;
import org.chungnamthon.flowmate.domain.challengeapplication.repository.ChallengeApplicationRepository;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationApproveServiceRequest;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationCreateServiceRequest;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;
import org.chungnamthon.flowmate.domain.challengeparticipant.repository.ChallengeParticipantRepository;
import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.global.exception.DuplicationException;
import org.chungnamthon.flowmate.global.exception.ForbiddenException;
import org.chungnamthon.flowmate.global.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
record ChallengeApplicationCommandServiceTest(
        ChallengeApplicationRepository challengeApplicationRepository,
        ChallengeApplicationCommandService challengeApplicationCommandService,
        ChallengeParticipantRepository challengeParticipantRepository,
        MemberRepository memberRepository,
        ChallengeRepository challengeRepository
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

    @DisplayName("챌린지 신청 여부 승인을 한다")
    @Test
    void approve() {
        var member = MemberFixture.createMember();
        var member2 = MemberFixture.createMember("testName","test12345@naver.com");
        memberRepository.save(member);
        memberRepository.save(member2);

        var challenge = ChallengeFixture.createChallenge();
        challengeRepository.save(challenge);

        var leader = ChallengeParticipant.createLeader(member, challenge);
        challengeParticipantRepository.save(leader);

        var application = ChallengeApplication.create(member2, challenge, "test-message");
        challengeApplicationRepository.save(application);
        // 승인 전 상태
        assertThat(application.getStatus()).isEqualTo(PENDING);

        var request = new ChallengeApplicationApproveServiceRequest(APPROVED, member.getId(), challenge.getId(), application.getId());
        challengeApplicationCommandService.approve(request);

        assertThat(application.getStatus()).isEqualTo(APPROVED);
    }

    @DisplayName("챌린지 참여자가 아닌 사람은 승인 여부를 결정할 수 없다.")
    @Test
    void failApproveToNotParticipant() {
        var member = MemberFixture.createMember();
        var generalMember = MemberFixture.createMember("testName","test12345@naver.com");
        memberRepository.save(member);

        var challenge = ChallengeFixture.createChallenge();
        challengeRepository.save(challenge);

        var leader = ChallengeParticipant.createLeader(member, challenge);
        challengeParticipantRepository.save(leader);

        var application = ChallengeApplication.create(member, challenge, "test-message");
        challengeApplicationRepository.save(application);
        // 승인 전 상태
        assertThat(application.getStatus()).isEqualTo(PENDING);

        var request = new ChallengeApplicationApproveServiceRequest(APPROVED, generalMember.getId(), challenge.getId(), application.getId());
        assertThatThrownBy(() -> challengeApplicationCommandService.approve(request))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("챌린지 참여자라도 리더가 아니면 승인 여부를 결정할 수 없다.")
    @Test
    void failApproveToNotLeader() {
        var generalMember = MemberFixture.createMember();
        var applyMember = MemberFixture.createMember("testName","test12345@naver.com");
        memberRepository.save(generalMember);
        memberRepository.save(applyMember);

        var challenge = ChallengeFixture.createChallenge();
        challengeRepository.save(challenge);

        var generalParticipant = ChallengeParticipant.createGeneral(generalMember, challenge);
        challengeParticipantRepository.save(generalParticipant);

        var application = ChallengeApplication.create(applyMember, challenge, "test-message");
        challengeApplicationRepository.save(application);
        // 승인 전 상태
        assertThat(application.getStatus()).isEqualTo(PENDING);

        var request = new ChallengeApplicationApproveServiceRequest(APPROVED, generalMember.getId(), challenge.getId(), application.getId());
        assertThatThrownBy(() -> challengeApplicationCommandService.approve(request))
            .isInstanceOf(ForbiddenException.class);
    }

    @DisplayName("이미 신청 결과가 결정되었다면 변경이 불가능하다.")
    @Test
    void failApproveToDuplicatedApplication() {
        var member = MemberFixture.createMember();
        var member2 = MemberFixture.createMember("testName","test12345@naver.com");
        memberRepository.save(member);
        memberRepository.save(member2);

        var challenge = ChallengeFixture.createChallenge();
        challengeRepository.save(challenge);

        var leader = ChallengeParticipant.createLeader(member, challenge);
        challengeParticipantRepository.save(leader);

        var application = ChallengeApplication.create(member2, challenge, "test-message");
        challengeApplicationRepository.save(application);

        var request = new ChallengeApplicationApproveServiceRequest(APPROVED, member.getId(), challenge.getId(), application.getId());
        challengeApplicationCommandService.approve(request);

        assertThat(application.getStatus()).isEqualTo(APPROVED);
        // 재승인 시도
        assertThatThrownBy(() -> challengeApplicationCommandService.approve(request))
            .isInstanceOf(DuplicationException.class);
    }

}