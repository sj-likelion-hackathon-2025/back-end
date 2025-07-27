package org.chungnamthon.flowmate.domain.challenge.repository;

import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeJpaRepository extends JpaRepository<Challenge, Long> {

}