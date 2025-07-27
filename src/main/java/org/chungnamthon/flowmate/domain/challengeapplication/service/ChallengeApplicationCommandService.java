package org.chungnamthon.flowmate.domain.challengeapplication.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challengeapplication.repository.ChallengeApplicationRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeApplicationCommandService {

    private final ChallengeApplicationRepository challengeApplicationRepository;

}