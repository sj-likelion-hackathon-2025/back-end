package org.chungnamthon.flowmate.domain.challenge.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeRepository {

    private final ChallengeJpaRepository challengeJpaRepository;

}