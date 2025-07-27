package org.chungnamthon.flowmate.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challenge.repository.ChallengeRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeCommandService {

    private final ChallengeRepository challengeRepository;

}