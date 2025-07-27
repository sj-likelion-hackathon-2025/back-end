package org.chungnamthon.flowmate.domain.challengeparticipant.repository;

import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeParticipantJpaRepository extends JpaRepository<ChallengeParticipant, Long> {

}