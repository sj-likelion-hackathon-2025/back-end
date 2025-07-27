package org.chungnamthon.flowmate.domain.challengeparticipant.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challengeparticipant.repository.ChallengeParticipantRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeParticipantCommandService {

    private final ChallengeParticipantRepository challengeParticipantRepository;

}