package org.chungnamthon.flowmate.domain.challengeapplication.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeApplicationRepository {

    private final ChallengeApplicationJpaRepository challengeApplicationJpaRepository;

}