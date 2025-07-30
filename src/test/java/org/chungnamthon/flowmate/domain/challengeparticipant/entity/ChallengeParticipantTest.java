package org.chungnamthon.flowmate.domain.challengeparticipant.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.chungnamthon.flowmate.domain.challenge.entity.ChallengeFixture;
import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChallengeParticipantTest {

    @DisplayName("챌린지 리더를 생성한다.")
    @Test
    void createLeader() {
        var member = MemberFixture.createMember();
        var challenge = ChallengeFixture.createChallenge();

        var leader = ChallengeParticipant.createLeader(member, challenge);

        assertThat(leader.getRole()).isEqualTo(ChallengeRole.LEADER);
    }

    @DisplayName("챌린지 일반 참여자를 생성한다.")
    @Test
    void createGeneral() {
        var member = MemberFixture.createMember();
        var challenge = ChallengeFixture.createChallenge();

        var general = ChallengeParticipant.createGeneral(member, challenge);

        assertThat(general.getRole()).isEqualTo(ChallengeRole.GENERAL);
    }

    @DisplayName("챌린지 참여자의 권한을 Leader로 변경한다.")
    @Test
    void updateRole() {
        var member = MemberFixture.createMember();
        var challenge = ChallengeFixture.createChallenge();
        var general = ChallengeParticipant.createGeneral(member, challenge);

        general.updateRole(ChallengeRole.LEADER);

        assertThat(general.getRole()).isEqualTo(ChallengeRole.LEADER);
    }
}