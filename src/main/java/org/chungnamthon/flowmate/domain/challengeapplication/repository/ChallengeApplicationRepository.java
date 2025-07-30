package org.chungnamthon.flowmate.domain.challengeapplication.repository;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challengeapplication.entity.ChallengeApplication;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeApplicationRepository {

    private final ChallengeApplicationJpaRepository challengeApplicationJpaRepository;

    public boolean existsByMemberIdAndChallengeId(Long memberId, Long challengeId) {
        return challengeApplicationJpaRepository.existsByMemberIdAndChallengeId(memberId, challengeId);
    }

    public void save(ChallengeApplication application) {
        challengeApplicationJpaRepository.save(application);
    }

}