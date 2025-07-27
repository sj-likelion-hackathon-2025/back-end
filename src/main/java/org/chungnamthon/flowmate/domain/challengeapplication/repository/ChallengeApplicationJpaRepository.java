package org.chungnamthon.flowmate.domain.challengeapplication.repository;

import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.challengeapplication.entity.ChallengeApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeApplicationJpaRepository extends JpaRepository<ChallengeApplication, Long> {

}