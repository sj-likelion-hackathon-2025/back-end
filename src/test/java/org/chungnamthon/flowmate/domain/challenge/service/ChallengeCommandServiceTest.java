package org.chungnamthon.flowmate.domain.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.challenge.entity.ChallengeFixture;
import org.chungnamthon.flowmate.domain.challenge.repository.ChallengeRepository;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeRole;
import org.chungnamthon.flowmate.domain.challengeparticipant.repository.ChallengeParticipantRepository;
import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
record ChallengeCommandServiceTest(
        ChallengeCommandService challengeCommandService,
        MemberRepository memberRepository,
        ChallengeRepository challengeRepository,
        ChallengeParticipantRepository challengeParticipantRepository,
        EntityManager entityManager
) {
    @DisplayName("챌린지 생성을 한다.")
    @Test
    void create() {
        var member = MemberFixture.createMember();
        memberRepository.save(member);

        var request = ChallengeFixture.getChallengeCreateServiceRequest(member.getId());
        var challengeId = challengeCommandService.create(request);

        var result = challengeRepository.findById(challengeId);

        assertThat(result)
                .extracting(Challenge::getTitle, Challenge::getCategory, Challenge:: getMaxParticipants)
                .containsExactly(request.title(),    request.category(),      request.maxParticipants());

        var participant = challengeParticipantRepository.findByMemberIdAndChallengeId(member.getId(), challengeId);
        assertThat(participant)
                .extracting(ChallengeParticipant::getChallenge, ChallengeParticipant::getMember,ChallengeParticipant::getRole)
                        .containsExactly(               result,                          member,         ChallengeRole.LEADER);
    }

}