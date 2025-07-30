package org.chungnamthon.flowmate.domain.challenge.repository;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.global.exception.NotFoundException;
import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeRepository {

    private final ChallengeJpaRepository challengeJpaRepository;

    public void save(Challenge challenge) {
        challengeJpaRepository.save(challenge);
    }

    public Challenge findById(Long id) {
        return challengeJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_CHALLENGE));
    }

}