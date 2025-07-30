package org.chungnamthon.flowmate.domain.challengeparticipant.repository;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;
import org.chungnamthon.flowmate.global.exception.NotFoundException;
import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeParticipantRepository {

    private final ChallengeParticipantJpaRepository challengeParticipantJpaRepository;

    public void save(ChallengeParticipant participant) {
        challengeParticipantJpaRepository.save(participant);
    }

    public ChallengeParticipant findByMemberIdAndChallengeId(Long memberId, Long challengeId) {
        return challengeParticipantJpaRepository.findByMemberIdAndChallengeId(memberId, challengeId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.FORBIDDEN_NOT_PARTICIPANT));
    }

}