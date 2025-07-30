package org.chungnamthon.flowmate.domain.challengeparticipant.repository;

import java.util.Optional;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeParticipantJpaRepository extends JpaRepository<ChallengeParticipant, Long> {

    Optional<ChallengeParticipant> findByMemberIdAndChallengeId(Long memberId, Long challengeId);

}