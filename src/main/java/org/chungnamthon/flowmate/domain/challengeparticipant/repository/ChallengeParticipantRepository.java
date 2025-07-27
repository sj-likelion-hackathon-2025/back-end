package org.chungnamthon.flowmate.domain.challengeparticipant.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeParticipantRepository {

    private final ChallengeParticipantJpaRepository challengeParticipantJpaRepository;

}