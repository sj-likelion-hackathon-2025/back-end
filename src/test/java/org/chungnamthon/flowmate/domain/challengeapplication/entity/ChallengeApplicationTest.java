package org.chungnamthon.flowmate.domain.challengeapplication.entity;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.challenge.entity.ChallengeFixture;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChallengeApplicationTest {

    ChallengeApplication challengeApplication;

    @BeforeEach
    void setUp() {
        Member member = MemberFixture.createMember();
        Challenge challenge = ChallengeFixture.createChallenge();
        challengeApplication = ChallengeApplication.create(member, challenge, "testMessage");
    }

    @DisplayName("챌린지 신청을 한다.")
    @Test
    void create() {
        assertThat(challengeApplication).isNotNull();
        Assertions.assertThat(challengeApplication.getMessage()).isEqualTo("testMessage");
    }

    @DisplayName("챌린지 신청 상태를 변경한다.")
    @Test
    void updateStatus() {
        assertThat(challengeApplication.getStatus()).isEqualTo(ApplicationStatus.PENDING);

        challengeApplication.updateStatus(ApplicationStatus.APPROVED);

        assertThat(challengeApplication.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
    }

    @DisplayName("신청 결과를 변경할 수 없다.")
    @Test
    void failUpdateStatus() {
        challengeApplication.updateStatus(ApplicationStatus.APPROVED);

        assertThatThrownBy(() -> challengeApplication.updateStatus(ApplicationStatus.REJECTED))
            .isInstanceOf(IllegalStateException.class);
    }
}