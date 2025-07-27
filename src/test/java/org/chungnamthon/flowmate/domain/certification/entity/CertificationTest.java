package org.chungnamthon.flowmate.domain.certification.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.challenge.entity.ChallengeFixture;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CertificationTest {

    @DisplayName("챌린지 인증을 생성한다.")
    @Test
    void create() {
        var member = MemberFixture.createMember();
        var challenge = ChallengeFixture.createChallenge();
        var participant = ChallengeParticipant.createGeneral(member, challenge);

        var certification = Certification.create(participant);

        assertThat(certification).isNotNull();
        assertThat(certification.getStatus()).isEqualTo(CertificationStatus.PENDING);
    }

    @DisplayName("인증 상태를 변경한다")
    @Test
    void updateStatus() {
        var member = MemberFixture.createMember();
        var challenge = ChallengeFixture.createChallenge();
        var participant = ChallengeParticipant.createGeneral(member, challenge);
        var certification = Certification.create(participant);

        certification.updateStatus(CertificationStatus.VERIFIED);

        assertThat(certification.getStatus()).isEqualTo(CertificationStatus.VERIFIED);

        // 인증 결롸를 다시 바꿀 수 없다.
        assertThatThrownBy(() ->    certification.updateStatus(CertificationStatus.REJECTED))
            .isInstanceOf(IllegalStateException.class);
    }

}